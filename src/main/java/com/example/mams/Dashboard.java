package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;


public class Dashboard implements Initializable {

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
    private Label totalAlbum_label;
    @FXML
    private Label totalCustomer_label;
    @FXML
    private Button totalAlbumButton;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
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

    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
      /*  try {
            getTotalAlbumLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        loadData();
        System.out.println("Hello");
    }
    @FXML
    private void getTotalAlbumLabel(ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        //obtain total albums available
        query = "SELECT SUM(QUANTITY_ON_HAND) AS SUM_QUANTITY from ALBUM";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        int TotalAlbum = 0;
        if(resultSet.next()){
            TotalAlbum = resultSet.getInt("SUM_QUANTITY");
        }
        totalAlbum_label.setText(Integer.toString(TotalAlbum));
        System.out.println(TotalAlbum);
        System.out.println("Hello");
    }
}




