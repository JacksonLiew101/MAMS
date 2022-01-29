package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesReport implements Initializable {

    // For Bar chart
    @FXML
    private BarChart<String, Integer> SalesChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    // <CustomerTable> is referring to the java class SalesReportTableClass
    private TableView<SalesReportTableClass> salesReportTable;
    @FXML
    private TableColumn<SalesReportTableClass, Integer> AlbumID_col;
    @FXML
    private TableColumn<SalesReportTableClass, String> AlbumName_col;
    @FXML
    private TableColumn<SalesReportTableClass, String> Artist_col;
    @FXML
    private TableColumn<SalesReportTableClass, Double> Price_col;
    @FXML
    private TableColumn<SalesReportTableClass, Integer> totalRentedAlbumQuantity_col;
    @FXML
    private TableColumn<SalesReportTableClass, Double> totalSales_col;

    @FXML
    private TextField searchTextfield;

    ObservableList<SalesReportTableClass> oblist = FXCollections.observableArrayList();

    public void loadData () {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            // For bar chart
            ResultSet rs1 = connectDB.createStatement().executeQuery("SELECT album.ALBUM_NAME, SUM(ar.QUANTITY_ALBUM_RENTED) as Total_Rented FROM album inner join album_rental as ar on album.ALBUM_ID = ar.ALBUM_ID GROUP BY ar.ALBUM_ID ORDER BY Total_Rented DESC LIMIT 5");
            ArrayList<String> albumName = new ArrayList<String>();
            ArrayList<Integer> totalRented = new ArrayList<Integer>();

            while (rs1.next()) {
                albumName.add(rs1.getString(1));
                totalRented.add(rs1.getInt(2));
            }
            rs1.close();

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            XYChart.Series<String, Integer> dataSeries1 = new XYChart.Series();

            for (int i = 0; i < albumName.size(); i++) {
                dataSeries1.getData().add(new XYChart.Data(totalRented.get(i), albumName.get(i)));
            }
            SalesChart.getData().addAll(dataSeries1);

            // For table
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT album.ALBUM_ID, album.ALBUM_NAME,  album.ARTIST, album.ALBUM_UNIT_PRICE, SUM(ar.QUANTITY_ALBUM_RENTED) as Total_Rented, SUM(ar.TOTAL_ALBUM_COST) as Total_Sales FROM album inner join album_rental as ar on album.ALBUM_ID = ar.ALBUM_ID GROUP BY ar.ALBUM_ID");


            while (rs.next()) {
                //columnLabel is referring to the SQL table column label
                oblist.add(new SalesReportTableClass(rs.getInt("ALBUM_ID"),
                        rs.getString("ALBUM_NAME"),
                        rs.getString("ARTIST"),
                        rs.getDouble("ALBUM_UNIT_PRICE"),
                        rs.getInt("Total_Rented"),
                        rs.getDouble("Total_Sales")));

            }
        } catch (SQLException ignored) {

        }
        //value here are referring to the attribute of class MusicAlbum
        AlbumID_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, Integer>("AlbumID"));
        AlbumName_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, String>("AlbumName"));
        Artist_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, String>("Artist"));
        Price_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, Double>("AlbumUnitPrice"));
        totalRentedAlbumQuantity_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, Integer>("TotalRentedAlbumQuantity"));
        totalSales_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass, Double>("TotalSales"));
        salesReportTable.setItems(oblist);

        //Initializing filtered list
        FilteredList<SalesReportTableClass> filteredData = new FilteredList<>(oblist, b->true);

        searchTextfield.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(salesReportTable -> {

                // If no search value then display all records or whatever records it current have. no changes.
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // true means found a match, false means no match found
                if(String.valueOf(salesReportTable.getAlbumID()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (salesReportTable.getAlbumName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (salesReportTable.getArtist().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });

            SortedList<SalesReportTableClass> sortedData = new SortedList<>(filteredData);

            // Bind sorted result with Table View
            sortedData.comparatorProperty().bind(salesReportTable.comparatorProperty());

            // Apply filtered and sorted data to the Table View
            salesReportTable.setItems(sortedData);

        });

    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        loadData();
    }
}



