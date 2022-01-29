package com.example.mams;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerRentalDetails implements Initializable{

    @FXML
    private TableView<RentalDetailsTable> rentalDetailsTable;

    @FXML
    private TableColumn<RentalDetailsTable, Integer> rentalID_col;

    @FXML
    private TableColumn<RentalDetailsTable, String> rentalDate_col;

    @FXML
    private TableColumn<RentalDetailsTable, String> rentalStatus_col;

    @FXML
    private TableColumn<RentalDetailsTable, Integer> albumQuantity_col;

    @FXML
    private TableColumn<RentalDetailsTable, String> albumName_col;

    @FXML
    private TableColumn<RentalDetailsTable, Double> totalCost_col;

    @FXML
    private TableColumn<RentalDetailsTable, String> return_col;

    @FXML
    private Label name_label;

    @FXML
    private Label album_label;

    @FXML
    private Label spending_label;

    @FXML
    private TextField searchTextfield;

    String query = null;
    Connection connection = null;
    PreparedStatement prepareStatement = null;
    RentalDetailsTable rentalDetailsTableClass = null;
    int customerID;

    ObservableList<RentalDetailsTable> rentalDetailsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void refreshData() {
        try{
            rentalDetailsList.clear();

            ResultSet rs = connection.createStatement().executeQuery("SELECT AR.RENTAL_ID, R.RENTAL_DATE, R.RENTAL_STATUS, AR.QUANTITY_ALBUM_RENTED, A.ALBUM_NAME, AR.TOTAL_ALBUM_COST FROM RENTAL R JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID "
                    +"WHERE CUSTOMER_ID = "+customerID+";");

            while(rs.next()) {
                //need to change slightly

                //columnLabel is referring to the SQL table column label
                rentalDetailsList.add(new RentalDetailsTable(rs.getInt("RENTAL_ID"),
                        rs.getString("RENTAL_DATE"),
                        rs.getString("RENTAL_STATUS"),
                        rs.getInt("QUANTITY_ALBUM_RENTED"),
                        rs.getString("ALBUM_NAME"),
                        rs.getDouble("TOTAL_ALBUM_COST")));

            }
        } catch (SQLException ignored){

        }

    }

    public void loadData() {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        refreshData();

        //add cell of button edit
        Callback<TableColumn<RentalDetailsTable, String>, TableCell<RentalDetailsTable, String>> cellFactory = (TableColumn<RentalDetailsTable, String> param) -> {
            // make cell containing buttons
            final TableCell<RentalDetailsTable, String> cell = new TableCell<RentalDetailsTable, String>() {
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
                            input1 = new FileInputStream("src/main/resources/com/example/mams/move-stock.png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Image icon1 = new Image(input1);
                        ImageView returnIcon = new ImageView(icon1);
                        returnIcon.setFitHeight(24);
                        returnIcon.setFitWidth(24);
                        returnIcon.setCursor(Cursor.HAND);

                        returnIcon.setOnMouseClicked((MouseEvent event) -> {
                            DialogBoxToShowNowIsReturned();
                            try {
                                rentalDetailsTableClass = rentalDetailsTable.getSelectionModel().getSelectedItem();
                                query = "UPDATE `RENTAL` SET `RENTAL_STATUS` = 'RETURNED' WHERE `RENTAL_ID` = "+rentalDetailsTableClass.getRentalID()+";";
                                DatabaseConnection connectNow = new DatabaseConnection();
                                connection = connectNow.getConnection();
                                prepareStatement = connection.prepareStatement(query);
                                prepareStatement.execute();
                                refreshData();

                            } catch (SQLException ignored) {

                            }


                        });

                        HBox manageBtn = new HBox(returnIcon);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(returnIcon, new Insets(2, 2, 0, 3));

                        setGraphic(manageBtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        //value here are referring to the attribute of class RentalDetailsTable
        rentalID_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Integer>("RentalID"));
        rentalDate_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("RentalDate"));
        rentalStatus_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("RentalStatus"));
        albumQuantity_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Integer>("QuantityAlbum"));
        albumName_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("AlbumName"));
        totalCost_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Double>("TotalCost"));
        return_col.setCellFactory(cellFactory);
        rentalDetailsTable.setItems(rentalDetailsList);


        //Initializing filtered list
        FilteredList<RentalDetailsTable> filteredData = new FilteredList<>(rentalDetailsList, b->true);

        searchTextfield.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(rentalDetailsTable -> {

                // If no search value then display all records or whatever records it current have. no changes.
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // true means found a match, false means no match found
                if(String.valueOf(rentalDetailsTable.getRentalID()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (rentalDetailsTable.getRentalDate().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (rentalDetailsTable.getRentalStatus().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (rentalDetailsTable.getAlbumName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else{
                    return false;
                }
            });

            SortedList<RentalDetailsTable> sortedData = new SortedList<>(filteredData);

            // Bind sorted result with Table View
            sortedData.comparatorProperty().bind(rentalDetailsTable.comparatorProperty());

            // Apply filtered and sorted data to the Table View
            rentalDetailsTable.setItems(sortedData);

        });




    }

    public void getAlbumNumberLabel() throws SQLException {

        int albumNo = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();

        //obtain total of album rented
        query = "SELECT SUM(AR.QUANTITY_ALBUM_RENTED) AS ALBUM_NO FROM RENTAL R JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID "
                +"WHERE CUSTOMER_ID = "+customerID+";";
        prepareStatement = connection.prepareStatement(query);
        ResultSet resultSet = prepareStatement.executeQuery();
        if(resultSet.next()){
            albumNo = resultSet.getInt("ALBUM_NO");
        }
        album_label.textProperty().bind(Bindings.format("Total Album Rented: %d",albumNo));
    }

    public void getSpendingLabel() throws SQLException {

        double totalSpending = 0.0;
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();

        //obtain total spending during rental
        query = "SELECT SUM(AR.TOTAL_ALBUM_COST) AS SPENDING FROM RENTAL R JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID "
                +"WHERE CUSTOMER_ID = "+customerID+";";
        prepareStatement = connection.prepareStatement(query);
        ResultSet resultSet = prepareStatement.executeQuery();
        if(resultSet.next()){
            totalSpending = resultSet.getDouble("SPENDING");
        }
        spending_label.textProperty().bind(Bindings.format("Total Spending: RM %.2f",totalSpending));
    }

    public void setCustomerDetails(int customerID, String customerName) throws SQLException {
        this.customerID = customerID;

        name_label.textProperty().bind(Bindings.format("%s's Rental History",customerName));
        getAlbumNumberLabel();
        getSpendingLabel();
    }

    private void DialogBoxToShowNowIsReturned(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Returning album");
        alert.setContentText("You are returning album to the stock");
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
