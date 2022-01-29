package com.example.mams;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditCustomer implements Initializable {

    @FXML
    private TextField FirstNameInput;
    @FXML
    private TextField LastNameInput;
    @FXML
    private TextField EmailInput;
    @FXML
    private TextField PhoneNoInput;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    DatabaseConnection connectNow = new DatabaseConnection();
    int customerId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void addNewCustomer(MouseEvent event){
        connection = connectNow.getConnection();
        //customer
        String FirstName = FirstNameInput.getText();
        String LastName = LastNameInput.getText();
        String Email = EmailInput.getText();
        String PhoneNo = PhoneNoInput.getText();

        if(FirstName.isEmpty()|| LastName.isEmpty() ||
                Email.isEmpty() || PhoneNo.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("PLEASE FILL IN ALL DATA");
            alert.showAndWait();
        }
        else
        {
            updateCustomer();
            DialogBoxInEditCustomer();
            clean();
        }
    }

    private void getCustomerQuery() {
        query = "UPDATE `CUSTOMER` SET "
                + "`CUSTOMER_F_NAME`=?,"
                + "`CUSTOMER_L_NAME`=?,"
                + "`CUSTOMER_EMAIL`=?,"
                + "`CUSTOMER_PHONE_NO`=? WHERE `CUSTOMER_ID` = " + customerId;
    }

    private void updateCustomer(){
        try{
            //update Customer Data
            getCustomerQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,FirstNameInput.getText());
            preparedStatement.setString(2,LastNameInput.getText());
            preparedStatement.setString(3,EmailInput.getText());
            preparedStatement.setString(4,PhoneNoInput.getText());
            preparedStatement.execute();
        } catch (SQLException ignored){}
    }

    @FXML
    private void clean(){
        FirstNameInput.setText(null);
        LastNameInput.setText(null);
        EmailInput.setText(null);
        PhoneNoInput.setText(null);
    }

    private void DialogBoxInEditCustomer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Details Edited Successfully!");
        alert.setContentText("The customer information is edited successfully");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isEmpty()){
            System.out.println("Alert closed");
        }
        else if (result.get() == ButtonType.OK){
            System.out.println("OK!");
        }
        else if (result.get() == ButtonType.CANCEL){
            System.out.println("Never!");
        }
    }

    public void setTextField(int customerID, String firstName, String lastName, String email, String phoneNo, int cardID) {
        customerId = customerID;
        FirstNameInput.setText(firstName);
        LastNameInput.setText(lastName);
        EmailInput.setText(email);
        PhoneNoInput.setText(phoneNo);
    }
}
