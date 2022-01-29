package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TextField searchTextfield;

    int customerID;

    ObservableList<RentalDetailsTable> rentalDetailsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loadData() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT AR.RENTAL_ID, R.RENTAL_DATE, R.RENTAL_STATUS, AR.QUANTITY_ALBUM_RENTED, A.ALBUM_NAME, AR.TOTAL_ALBUM_COST FROM RENTAL R JOIN ALBUM_RENTAL AR ON R.RENTAL_ID = AR.RENTAL_ID JOIN ALBUM A ON AR.ALBUM_ID = A.ALBUM_ID "
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

        //value here are referring to the attribute of class RentalDetailsTable
        rentalID_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Integer>("RentalID"));
        rentalDate_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("RentalDate"));
        rentalStatus_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("RentalStatus"));
        albumQuantity_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Integer>("QuantityAlbum"));
        albumName_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,String>("AlbumName"));
        totalCost_col.setCellValueFactory(new PropertyValueFactory<RentalDetailsTable,Double>("TotalCost"));
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

    // can change to set customer name and customer ID

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    //maybe set customer id

}
