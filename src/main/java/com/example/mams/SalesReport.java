package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesReport {

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

    ObservableList<SalesReportTableClass> oblist = FXCollections.observableArrayList();

    public void connectButton (ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT album.ALBUM_ID, album.ALBUM_NAME,  album.ARTIST, album.ALBUM_UNIT_PRICE, SUM(ar.QUANTITY_ALBUM_RENTED) as Total_Rented, SUM(ar.TOTAL_ALBUM_COST) as Total_Sales FROM album inner join album_rental as ar on album.ALBUM_ID = ar.ALBUM_ID GROUP BY ar.ALBUM_ID");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                oblist.add(new SalesReportTableClass(rs.getInt("ALBUM_ID"),
                        rs.getString("ALBUM_NAME"),
                        rs.getString("ARTIST"),
                        rs.getDouble("ALBUM_UNIT_PRICE"),
                        rs.getInt("Total_Rented"),
                        rs.getDouble("Total_Sales")));

            }
        } catch (SQLException ignored){

        }
        //value here are referring to the attribute of class MusicAlbum
        AlbumID_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,Integer>("AlbumID"));
        AlbumName_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,String>("AlbumName"));
        Artist_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,String>("Artist"));
        Price_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,Double>("AlbumUnitPrice"));
        totalRentedAlbumQuantity_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,Integer>("TotalRentedAlbumQuantity"));
        totalSales_col.setCellValueFactory(new PropertyValueFactory<SalesReportTableClass,Double>("TotalSales"));
        salesReportTable.setItems(oblist);

    }
}
