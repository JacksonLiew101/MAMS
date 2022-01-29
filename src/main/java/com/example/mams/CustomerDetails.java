package com.example.mams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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
    @FXML
    private TableColumn<CustomerTable, String> button_col;

    String query = null;
    Connection connection = null;
    PreparedStatement prepareStatement = null;
    CustomerTable customerTable = null;

    ObservableList<CustomerTable> CustomerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddCustomer.setCursor(Cursor.HAND);
        RefreshButton.setCursor(Cursor.HAND);
        loadData();
    }

    private void refreshData() {
        try{
            CustomerList.clear();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM `CUSTOMER`");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                CustomerList.add(new CustomerTable(rs.getInt("CUSTOMER_ID"),
                        rs.getString("CUSTOMER_F_NAME"),
                        rs.getString("CUSTOMER_L_NAME"),
                        rs.getString("CUSTOMER_EMAIL"),
                        rs.getString("CUSTOMER_PHONE_NO"),
                        rs.getInt("CARD_ID")));

            }
        } catch (SQLException ignored){}

    }

    public void loadData(){
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        refreshData();

        //add cell of button edit
        Callback<TableColumn<CustomerTable, String>, TableCell<CustomerTable, String>> cellFactory = (TableColumn<CustomerTable, String> param) -> {
            // make cell containing buttons
            final TableCell<CustomerTable, String> cell = new TableCell<CustomerTable, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FileInputStream input1 = null;
                        try {
                            input1 = new FileInputStream("src/main/resources/com/example/mams/delete-sign.png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Image icon1 = new Image(input1);
                        ImageView deleteIcon = new ImageView(icon1);
                        deleteIcon.setFitHeight(24);
                        deleteIcon.setFitWidth(24);
                        deleteIcon.setCursor(Cursor.HAND);

                        FileInputStream input2 = null;
                        try {
                            input2 = new FileInputStream("src/main/resources/com/example/mams/edit-property.png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Image icon2 = new Image(input2);
                        ImageView editIcon = new ImageView(icon2);
                        editIcon.setFitHeight(24);
                        editIcon.setFitWidth(24);
                        editIcon.setCursor(Cursor.HAND);

                        FileInputStream input3 = null;
                        try {
                            input3 = new FileInputStream("src/main/resources/com/example/mams/info.png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Image icon3 = new Image(input3);
                        ImageView detailIcon = new ImageView(icon3);
                        detailIcon.setFitHeight(24);
                        detailIcon.setFitWidth(24);
                        detailIcon.setCursor(Cursor.HAND);

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            DialogBoxToShowNowIsDeletingData();
                            try {
                                customerTable = CustomerTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `CARD` WHERE `CARD_ID` ="+customerTable.getCardID(); // Delete from CARD to directly delete the customer details too
                                DatabaseConnection connectNow = new DatabaseConnection();
                                connection = connectNow.getConnection();
                                prepareStatement = connection.prepareStatement(query);
                                prepareStatement.execute();
                                refreshData();

                            } catch (SQLException ignored) {

                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            DialogBoxToShowNowIsEditData();
                            customerTable = CustomerTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditCustomer.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ignored) {
                            }

                            EditCustomer editCustomer = loader.getController();
                            editCustomer.setTextField(customerTable.getCustomerID(), customerTable.getFirstName(), customerTable.getLastName(), customerTable.getEmail(),customerTable.getPhoneNo(), customerTable.getCardID());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });
                        detailIcon.setOnMouseClicked((MouseEvent event) -> {

                            //view rental detail, top table show rental summary, bottom table show by album, new fxml

                            /*
                            SELECT AR.RENTAL_ID, R.RENTAL_DATE, R.RENTAL_STATUS, AR.QUANTITY_ALBUM_RENTED, A.ALBUM_NAME, AR.TOTAL_ALBUM_COST FROM RENTAL R
JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID
JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID
WHERE CUSTOMER_ID = 10000;


                            CustomerRentalDetails customerRentalDetails = loader.getController();
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                             */



                        });

                        HBox manageBtn = new HBox(editIcon, deleteIcon, detailIcon);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(detailIcon, new Insets(2, 3, 0, 2));

                        setGraphic(manageBtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        //value here are referring to the attribute of class CustomerTable
        CustomerID.setCellValueFactory(new PropertyValueFactory<CustomerTable,Integer>("CustomerID"));
        FirstName.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("FirstName"));
        LastName.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("LastName"));
        Email.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("Email"));
        PhoneNo.setCellValueFactory(new PropertyValueFactory<CustomerTable,String>("PhoneNo"));
        CardID.setCellValueFactory(new PropertyValueFactory<CustomerTable,Integer>("CardID"));
        button_col.setCellFactory(cellFactory);
        CustomerTable.setItems(CustomerList);
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

    @FXML
    private void refreshTable(MouseEvent event){
        refreshData();
    }

    private void DialogBoxToShowNowIsInsertNewData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add new customer");
        alert.setContentText("You are adding new customer in this database");
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
    private void DialogBoxToShowNowIsEditData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editing existing customer");
        alert.setContentText("You are editing existing customer in this database");
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
    private void DialogBoxToShowNowIsDeletingData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleting existing customer and related detail");
        alert.setContentText("This customer and related detail is deleted from this database successfully");
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

}
