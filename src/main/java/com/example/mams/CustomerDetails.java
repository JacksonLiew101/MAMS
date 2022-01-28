package com.example.mams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerDetails implements Initializable {

    @FXML
    private Button AddCustomer;
    @FXML
    private Button RefreshButton;
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
    ObservableList<CustomerTable> CustomerList = FXCollections.observableArrayList();


    public void loadData(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM CUSTOMER");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                CustomerList.add(new CustomerTable(rs.getInt("CUSTOMER_ID"),
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
        CustomerTable.setItems(CustomerList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddCustomer.setCursor(Cursor.HAND);
        RefreshButton.setCursor(Cursor.HAND);
        loadData();
    }

    @FXML
    private void refreshTable(MouseEvent event){
        CustomerList.clear();
        loadData();
    }

    @FXML
    private void AddNewCustomer(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddNewCustomer.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }


}
