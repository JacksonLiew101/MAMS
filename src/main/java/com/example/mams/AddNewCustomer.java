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

public class AddNewCustomer implements Initializable {

    @FXML
    private TextField FirstNameInput;
    @FXML
    private TextField LastNameInput;
    @FXML
    private TextField EmailInput;
    @FXML
    private TextField PhoneNoInput;
    @FXML
    private TextField CardNumberInput;
    @FXML
    private TextField CardExpiryYearInput;
    @FXML
    private TextField CardExpiryMonthInput;
    @FXML
    private TextField CVVInput;
    @FXML
    private TextField CardHolderNameInput;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    DatabaseConnection connectNow = new DatabaseConnection();
    private boolean update;
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
        //card
        String CardNumberText  =CardNumberInput.getText();
        String CardExpiryYearText = CardExpiryYearInput.getText();
        String CardExpiryMonthText = CardExpiryMonthInput.getText();
        String CVVText  =CVVInput.getText();
        String CardHolderName = CardHolderNameInput.getText();

//        int CardNumber=  Integer.parseInt(CardNumberText);
//        int  CardExpiryYear = Integer.parseInt(CardExpiryYearText);
//        int CardExpiryMonth =  Integer.parseInt(CardExpiryMonthText);
//        int  CVV = Integer.parseInt(CVVText);

        if(FirstName.isEmpty()|| LastName.isEmpty() ||
                Email.isEmpty() || PhoneNo.isEmpty() ||
                CardNumberText.isEmpty()|| CardExpiryYearText.isEmpty()||
                CardExpiryMonthText.isEmpty()|| CVVText.isEmpty()||
                CardHolderName.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("PLEASE FILL IN ALL DATA");
            alert.showAndWait();
        }
        else
        {
            getCardQuery();
            insertCard();
            insertCustomer();
            DialogBoxInAddNewCustomer();
            clean();
        }
    }
    private void getCardQuery() {
        query = "INSERT INTO `CARD` (`CARD_NO`, `CARD_EXPIRY_YEAR`, `CARD_EXPIRY_MONTH`, `CVV`, `CARDHOLDER_NAME`) VALUES (?,?,?,?,?)";
    }

    private void getCustomerQuery() {
        query = "INSERT INTO `CUSTOMER` (`CUSTOMER_F_NAME`, `CUSTOMER_L_NAME`, `CUSTOMER_EMAIL`, `CUSTOMER_PHONE_NO`, `CARD_ID`) VALUES (?,?,?,?,?)";
    }

    private void insertCustomer(){
        try{
            //obtain CARD ID
            query = "SELECT `CARD_ID` FROM CARD WHERE `CARD_NO` = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,CardNumberInput.getText());
            resultSet = preparedStatement.executeQuery();
            String CardID = null;
            if(resultSet.next()){
                CardID = resultSet.getString("CARD_ID");
            }
            //insert Customer Data
            getCustomerQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,FirstNameInput.getText());
            preparedStatement.setString(2,LastNameInput.getText());
            preparedStatement.setString(3,EmailInput.getText());
            preparedStatement.setString(4,PhoneNoInput.getText());
            preparedStatement.setString(5,CardID);
            preparedStatement.execute();
        } catch (SQLException ignored){}
    }
    private void insertCard(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,CardNumberInput.getText());
            preparedStatement.setString(2,CardExpiryYearInput.getText());
            preparedStatement.setString(3,CardExpiryMonthInput.getText());
            preparedStatement.setString(4,CVVInput.getText());
            preparedStatement.setString(5,CardHolderNameInput.getText());
            preparedStatement.execute();
        } catch (SQLException ignored){}
    }

    @FXML
    private void clean(){
        FirstNameInput.setText(null);
        LastNameInput.setText(null);
        EmailInput.setText(null);
        PhoneNoInput.setText(null);
        CardNumberInput.setText(null);
        CardExpiryYearInput.setText(null);
        CardExpiryMonthInput.setText(null);
        CVVInput.setText(null);
        CardHolderNameInput.setText(null);
    }

    private void DialogBoxInAddNewCustomer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Customer Added Successfully!");
        alert.setContentText("The new customer information is added successfully");
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

    public void setUpdate(boolean b) {
        this.update = b;
    }

    public void setTextField(int customerID, String firstName, String lastName, String email, String phoneNo, int cardID) {
        customerId = customerID;
        FirstNameInput.setText(firstName);
        LastNameInput.setText(lastName);
        EmailInput.setText(email);
        PhoneNoInput.setText(phoneNo);
        CardNumberInput.setText(String.valueOf(cardID));
        /*
        CardExpiryYearInput;

        CardExpiryMonthInput;

        CVVInput;

        CardHolderNameInput;

         */
    }
}
