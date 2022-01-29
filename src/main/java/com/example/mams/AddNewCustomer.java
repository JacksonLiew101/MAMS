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
    String CreditCardType;
    int customerId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
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

        long CardNumber=  Long.parseLong(CardNumberText);
        System.out.println(CardNumber);
        int  CardExpiryYear = Integer.parseInt(CardExpiryYearText);
        int CardExpiryMonth =  Integer.parseInt(CardExpiryMonthText);
        int  CVV = Integer.parseInt(CVVText);

        if(FirstName.isEmpty()|| LastName.isEmpty() ||
                Email.isEmpty() || PhoneNo.isEmpty() ||
                CardNumberText.isEmpty()|| CardExpiryYearText.isEmpty()||
                CardExpiryMonthText.isEmpty()|| CVVText.isEmpty()||
                CardHolderName.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in ALL DATA");
            alert.showAndWait();
        }
        else if (PhoneNo.length() != 12)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in the country code for the phone no or insert correct phone no");
            alert.showAndWait();
            clean();
        }
        else if(!isValid(Email))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in correct email format");
            alert.showAndWait();
            clean();
        }
        else if(!validitychk(CardNumber))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in correct credit/debit card number");
            alert.showAndWait();
            clean();
        }
        else if(CardExpiryYear < 1000 || CardExpiryYear > 4000)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in correct card expiry year");
            alert.showAndWait();
            clean();
        }
        else if(CardExpiryMonth < 1 || CardExpiryMonth > 12)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in correct card expiry month");
            alert.showAndWait();
            clean();
        }
        else if (CVV < 100 || CVV > 999)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in correct CVV");
            alert.showAndWait();
            clean();
        }
        else
        {
            //display the credit card type
            if(prefixcheck(CardNumber, 4)){
                CreditCardType = "Visa";
            }else if(prefixcheck(CardNumber, 5)){
                CreditCardType = "Master";
            }else if(prefixcheck(CardNumber, 6)){
                CreditCardType = "Discover";
            }else if(prefixcheck(CardNumber, 37)){
                CreditCardType = "American Express";
            }else {
                CreditCardType = "any other types of";
            }
            getCardQuery();
            insertCard();
            insertCustomer();
            DialogBoxInAddNewCustomer(CreditCardType);
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

    private void DialogBoxInAddNewCustomer(String creditCardType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Customer Added Successfully!");
        alert.setContentText("The new customer information is added successfully. Your "+ CreditCardType + " card is accepted.");
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
    // valid the credit card number input
    // Return true if the card number is valid
    public static boolean validitychk(long cnumber) {
        return (thesize(cnumber) >= 13 && thesize(cnumber) <= 16) && (prefixmatch(cnumber, 4)
                || prefixmatch(cnumber, 5) || prefixmatch(cnumber, 37) || prefixmatch(cnumber, 6))
                && ((sumdoubleeven(cnumber) + sumodd(cnumber)) % 10 == 0);
    }
    // Get the result from Step 2
    public static int sumdoubleeven(long cnumber) {
        int sum = 0;
        String num = cnumber + "";
        for (int i = thesize(cnumber) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);
        return sum;
    }
    // Return this cnumber if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int cnumber) {
        if (cnumber < 9)
            return cnumber;
        return cnumber / 10 + cnumber % 10;
    }
    // Return sum of odd-place digits in cnumber
    public static int sumodd(long cnumber) {
        int sum = 0;
        String num = cnumber + "";
        for (int i = thesize(cnumber) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }
    // Return true if the digit d is a prefix for cnumber
    public static boolean prefixmatch(long cnumber, int d) {
        return getprefx(cnumber, thesize(d)) == d;
    }
    // Return the number of digits in d
    public static int thesize(long d) {
        String num = d + "";
        return num.length();
    }
    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getprefx(long cnumber, int k) {
        if (thesize(cnumber) > k) {
            String num = cnumber + "";
            return Long.parseLong(num.substring(0, k));
        }
        return cnumber;
    }

    // to check the card type
    public static int sizecheck(long c_num) {
        String num = c_num+"";
        return num.length();
    }
    public static long getprefix(long c_num, int k) {
        if(sizecheck(c_num)>k) {
            String num = c_num + "";
            return Long.parseLong(num.substring(0, k));
        }
        return c_num;
    }
    public static boolean prefixcheck(long c_num, int d) {
        return getprefix(c_num, sizecheck(d)) == d;
    }
}

