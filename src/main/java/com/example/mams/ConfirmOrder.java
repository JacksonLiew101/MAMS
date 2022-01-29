package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfirmOrder implements Initializable {

    @FXML
    private Label TotalCostLabel;
    @FXML
    private Button ConfirmButton;
    @FXML
    private Label CustomerIDLabel;
    @FXML
    private Label RentalIDLabel;

    @FXML
    private TableView<ConfirmOrderClass> ConfirmOrderTable;

    @FXML
    private TableColumn<ConfirmOrderClass, String> AlbumName_col;
    @FXML
    private TableColumn<ConfirmOrderClass, Integer> QuantityRented_col;
    @FXML
    private TableColumn<ConfirmOrderClass, Double> AlbumUnitPriceWhenRented_col;
    @FXML
    private TableColumn<ConfirmOrderClass, Double> TotalCostAlbum_col;
    @FXML
    private TableColumn<ConfirmOrderClass, String>  rentalStatus_col;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    ObservableList<ConfirmOrderClass> oblist = FXCollections.observableArrayList();

    public void loadData() {


        try{            
            DatabaseConnection connectNow = new DatabaseConnection();
            connection = connectNow.getConnection();
            query = "SELECT   A.ALBUM_NAME, AR.QUANTITY_ALBUM_RENTED,AR.ALBUM_UNIT_PRICE_WHEN_RENTED,  AR.TOTAL_ALBUM_COST, R.RENTAL_STATUS FROM RENTAL R JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID WHERE AR.RENTAL_ID = ?";
            System.out.println(RentalIDLabel.getText());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,RentalIDLabel.getText());
            resultSet = preparedStatement.executeQuery();


            while(resultSet.next()) {
                //columnLabel is referring to the SQL table column label
                oblist.add(new ConfirmOrderClass(
                        resultSet.getString("ALBUM_NAME"),
                        resultSet.getInt("QUANTITY_ALBUM_RENTED"),
                        resultSet.getDouble("ALBUM_UNIT_PRICE_WHEN_RENTED"),
                        resultSet.getDouble("TOTAL_ALBUM_COST"),
                        resultSet.getString("RENTAL_STATUS")));
            }
        } catch (SQLException ignored){

        }
        //value here are referring to the attribute of class ConfirmOrder
        AlbumName_col.setCellValueFactory(new PropertyValueFactory<ConfirmOrderClass, String>("AlbumName"));
        QuantityRented_col.setCellValueFactory(new PropertyValueFactory<ConfirmOrderClass, Integer>("QuantityAlbumRented"));
        AlbumUnitPriceWhenRented_col.setCellValueFactory(new PropertyValueFactory<ConfirmOrderClass, Double>("AlbumUnitPriceWhenRented"));
        TotalCostAlbum_col.setCellValueFactory(new PropertyValueFactory<ConfirmOrderClass, Double>("TotalAlbumCost"));
        rentalStatus_col.setCellValueFactory(new PropertyValueFactory<ConfirmOrderClass, String>("RentalStatus"));
        ConfirmOrderTable.setItems(oblist);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void runConfirmOrder()
    {
        try {
            getCustomerIDLabel();
            System.out.println(RentalIDLabel.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadData();
        try {
            getTotalCostLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRentalLabel(String RentalIDLabelInput)
    {
        RentalIDLabel.setText(RentalIDLabelInput);
    }

    public void getCustomerIDLabel() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        //obtain CARD ID
        query = "SELECT `CUSTOMER_ID` FROM RENTAL WHERE `RENTAL_ID` = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,RentalIDLabel.getText());
        resultSet = preparedStatement.executeQuery();
        String CustomerID = null;
        if(resultSet.next()){
            CustomerID = resultSet.getString("CUSTOMER_ID");
        }
        CustomerIDLabel.setText(CustomerID);
    }
    public void getTotalCostLabel() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        //obtain CARD ID
        query = "SELECT SUM(TOTAL_ALBUM_COST) AS SUM_COST FROM `ALBUM_RENTAL` WHERE RENTAL_ID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,RentalIDLabel.getText());
        resultSet = preparedStatement.executeQuery();
        String TotalCost = null;
        if(resultSet.next()){
            TotalCost = resultSet.getString("SUM_COST");
        }
        TotalCostLabel.setText(TotalCost);
    }

}
