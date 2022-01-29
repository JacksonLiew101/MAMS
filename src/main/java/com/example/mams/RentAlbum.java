package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import org.controlsfx.control.action.Action;

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

public class RentAlbum implements Initializable {
    @FXML
    // <CustomerTable> is referring to the java class MusicAlbum
    private TableView<MusicAlbum> AlbumTable;
    @FXML
    private TableColumn<MusicAlbum, Integer> albumID_col;
    @FXML
    private TableColumn<MusicAlbum,String> albumName_col;
    @FXML
    private TableColumn<MusicAlbum,String> artist_col;
    @FXML
    private TableColumn<MusicAlbum,String> genre_col;
    @FXML
    private TableColumn<MusicAlbum,Integer> yearOfRelease_col;
    @FXML
    private TableColumn<MusicAlbum,Integer> quantityOnHand_col;
    @FXML
    private TableColumn<MusicAlbum,Integer> albumUnitPrice_col;

    // for input new rental
    @FXML
    private TextField CustomerIDInput;
    @FXML
    private DatePicker RentalDateInput;
    @FXML
    private Button MakeNewRentalButton,clearButton,refreshButton;
    @FXML
    private TextField searchTextfield;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    String LatestRentalID = null;
    // until here for inputting the new rental order
    @FXML
    private Label showRentalID;
    @FXML
    private TableColumn<MusicAlbum, String> button_col;
    @FXML
    private Button  confirmButton, cancelButton;


    MusicAlbum musicAlbum = null;
//    DatabaseConnection connectNow = new DatabaseConnection();
//    Connection connectDB = connectNow.getConnection();
    ObservableList<MusicAlbum> MusicAlbumList = FXCollections.observableArrayList();
    public void loadData(){

        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        refreshData();

        //add cell of button edit
        Callback<TableColumn<MusicAlbum, String>, TableCell<MusicAlbum, String>> cellFactory = (TableColumn<MusicAlbum, String> param) -> {
            // make cell containing buttons
            final TableCell<MusicAlbum, String> cell = new TableCell<MusicAlbum, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FileInputStream input2 = null;
                        try {
                            input2 = new FileInputStream("src/main/resources/com/example/mams/plus-math.png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Image icon2 = new Image(input2);
                        ImageView addIcon = new ImageView(icon2);
                        addIcon.setFitHeight(24);
                        addIcon.setFitWidth(24);
                        addIcon.setCursor(Cursor.HAND);


                        addIcon.setOnMouseClicked((MouseEvent event) -> {

                            DialogBoxToShowNowIsEditData();
                            musicAlbum = AlbumTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("ConfirmRentalAlbumQuantity.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ignored) {
                            }
                            ConfirmRentalAlbumQuantity confirmRentalAlbumQuantity = loader.getController();
//                            public void setLabel(String albumIDLabel, String albumNameLabel, String albumUnitPriceLabel,
//                                    String artistLabel, String genreLabel, String  rentalIDLabel,
//                                    String stockAvailableLabel, String yearOfReleaseLabel)
                            confirmRentalAlbumQuantity.setLabel(String.valueOf(musicAlbum.getAlbumID()), musicAlbum.getAlbumName(),
                                    String.valueOf(musicAlbum.getAlbumUnitPrice()), musicAlbum.getArtist(),
                                    musicAlbum.getGenre(), showRentalID.getText(),
                                    String.valueOf(musicAlbum.getQuantityOnHand()), String.valueOf(musicAlbum.getYearOfRelease()) );
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        HBox manageBtn = new HBox(addIcon);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(addIcon, new Insets(2, 3, 0, 2));

                        setGraphic(manageBtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        //value here are referring to the attribute of class MusicAlbum
        albumID_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("AlbumID"));
        albumName_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("AlbumName"));
        artist_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("Artist"));
        genre_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("Genre"));
        yearOfRelease_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("YearOfRelease"));
        quantityOnHand_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("QuantityOnHand"));
        albumUnitPrice_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("AlbumUnitPrice"));
        button_col.setCellFactory(cellFactory);
        AlbumTable.setItems(MusicAlbumList);

        //Initializing filtered list
        FilteredList<MusicAlbum> filteredData = new FilteredList<>(MusicAlbumList, b->true);

        searchTextfield.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(musicAlbum -> {

                // If no search value then display all records or whatever records it current have. no changes.
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // true means found a match, false means no match found
                if(String.valueOf(musicAlbum.getAlbumID()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (musicAlbum.getAlbumName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (musicAlbum.getArtist().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (musicAlbum.getGenre().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if (String.valueOf(musicAlbum.getYearOfRelease()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });

            SortedList<MusicAlbum> sortedData = new SortedList<>(filteredData);

            // Bind sorted result with Table View
            sortedData.comparatorProperty().bind(AlbumTable.comparatorProperty());

            // Apply filtered and sorted data to the Table View
            AlbumTable.setItems(sortedData);

        });

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        MakeNewRentalButton.setCursor(Cursor.HAND);
        clearButton.setCursor(Cursor.HAND);
        confirmButton.setCursor(Cursor.HAND);
        cancelButton.setCursor(Cursor.HAND);
        refreshButton.setCursor(Cursor.HAND);
        loadData();
    }



    @FXML
    private void ConfirmOrderPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("ConfirmOrder.fxml"));
        try {
            loader.load();
        } catch (IOException ignored) {
        }
        //stop here
        ConfirmOrder ConfirmOrder = loader.getController();

        ConfirmOrder.setRentalLabel(showRentalID.getText());
        ConfirmOrder.runConfirmOrder();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }


    private void DialogBoxToShowNowIsEditData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rental Album");
        alert.setContentText("You are renting album");
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


    @FXML
    private void addNewRental(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        String customerID = CustomerIDInput.getText();
        String RentalDate = String.valueOf(RentalDateInput.getValue());
        if(customerID.isEmpty()|| RentalDate.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("PLEASE FILL IN ALL DATA");
            alert.showAndWait();
        }
        else {
            getQuery();
            insert();
            DialogBoxInAddNewRental();
            System.out.println(this.LatestRentalID);
            showRentalID.setText(getLatestRentalID());
            clear();
        }
    }

    private void getQuery() {
        query = "INSERT INTO `RENTAL` (`CUSTOMER_ID`, `RENTAL_DATE`) VALUES (?,?)";
    }

    public String getRentalID() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        //obtain CARD ID
        query = "SELECT `RENTAL_ID` FROM RENTAL WHERE `CUSTOMER_ID` = ? AND `RENTAL_DATE` = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,CustomerIDInput.getText());
        preparedStatement.setString(2,String.valueOf(RentalDateInput.getValue()));
        resultSet = preparedStatement.executeQuery();
        String RentalID = null;
        if(resultSet.next()){
            RentalID = resultSet.getString("RENTAL_ID");
        }
        return RentalID;
    }

    private void insert(){
        query = "INSERT INTO `RENTAL` (`CUSTOMER_ID`, `RENTAL_DATE`) VALUES (?,?)";
        try{
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,CustomerIDInput.getText());
            preparedStatement.setString(2,String.valueOf(RentalDateInput.getValue()));
            preparedStatement.execute();
            LatestRentalID = getRentalID();
        } catch (SQLException ignored){}
    }

    public String getLatestRentalID(){
        return this.LatestRentalID;
    }
    @FXML
    private void clear(){
        CustomerIDInput.setText(null);
        RentalDateInput.setValue(null);
    }


    private void DialogBoxInAddNewRental() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Rental Added Successfully!");
        alert.setContentText("The new rental information is added successfully");
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

    @FXML
    private void Refresh(ActionEvent event){
        refreshData();
    }

    private void refreshData() {
        try {
            MusicAlbumList.clear();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM `ALBUM`");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                MusicAlbumList.add(new MusicAlbum(rs.getInt("ALBUM_ID"),
                        rs.getString("ALBUM_NAME"),
                        rs.getString("ARTIST"),
                        rs.getString("GENRE"),
                        rs.getInt("YEAR_OF_RELEASE"),
                        rs.getInt("QUANTITY_ON_HAND"),
                        rs.getInt("ALBUM_UNIT_PRICE") ));
            }
        } catch(SQLException ignored){}
    }

    @FXML
    private void CancelOrder(ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
        query = "UPDATE `RENTAL` SET `RENTAL_STATUS` = 'RETURNED' WHERE RENTAL_ID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,showRentalID.getText());
        preparedStatement.executeUpdate();

        query = "DELETE FROM RENTAL WHERE RENTAL_ID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,showRentalID.getText());
        preparedStatement.executeUpdate();
//        query = "DELETE RENTAL WHERE `RENTAL_ID` = ?";
//        preparedStatement.setString(1,showRentalID.getText());
//        resultSet = preparedStatement.executeQuery();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your order is canceled!");
        alert.setContentText("Your order is canceled. You may close the window and make new order.");
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
        System.out.println(query);
        showRentalID.setText(null);
        clear();
    }

}
