package com.example.mams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDetails {

    @FXML
   private Button connectButton;
    @FXML
    // <CustomerTable> is referring to the java class CustomerTables
    private TableView<CustomerTable> CustomerTable;
    @FXML
    private TableColumn<CustomerTable, Integer> CustomerID;
    @FXML
    private TableColumn<CustomerTable,String> FirstName;
    @FXML
    private TableColumn<CustomerTable,String> LastName;
    @FXML
    private TableColumn<CustomerTable,String> Email;
    @FXML
    private TableColumn<CustomerTable,String> PhoneNo;
    @FXML
    private TableColumn<CustomerTable,Integer> CardID;
    ObservableList<CustomerTable> oblist = FXCollections.observableArrayList();

    public void connectButton (ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM CUSTOMER");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                oblist.add(new CustomerTable(rs.getInt("CUSTOMER_ID"),
                        rs.getString("CUSTOMER_F_NAME"),
                        rs.getString("CUSTOMER_L_NAME"),
                        rs.getString("CUSTOMER_EMAIL"),
                        rs.getString("CUSTOMER_PHONE_NO"),
                        rs.getInt("CARD_ID")));

            }
        } catch (SQLException ignored){

        }
        //value here are referring to the attribute of class CustomerTable
        CustomerID.setCellValueFactory(new PropertyValueFactory<CustomerTable,Integer>("CustomerID"));
        FirstName.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("FirstName"));
        LastName.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("LastName"));
        Email.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("Email"));
        PhoneNo.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("PhoneNo"));
        CardID.setCellValueFactory(new PropertyValueFactory<CustomerTable,Integer>("CardID"));
        CustomerTable.setItems(oblist);




    }

}
