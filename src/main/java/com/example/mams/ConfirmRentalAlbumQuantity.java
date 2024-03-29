package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConfirmRentalAlbumQuantity implements Initializable {

    @FXML
    private Label AlbumIDLabel;

    @FXML
    private Label AlbumNameLabel;

    @FXML
    private Label AlbumUnitPriceLabel;

    @FXML
    private Label ArtistLabel;

    @FXML
    private Button ConfirmButton;

    @FXML
    private Label GenreLabel;

    @FXML
    private Label RentalIDLabel;

    @FXML
    private Label StockAvailableLabel;

    @FXML
    private Label YearOfReleaseLabel;

    @FXML
    private TextField quantityRented;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    DatabaseConnection connectNow = new DatabaseConnection();
    MusicAlbum musicAlbum = null;
    ObservableList<MusicAlbum> MusicAlbumList = FXCollections.observableArrayList();

    int stockAvailable = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfirmButton.setCursor(Cursor.HAND);
        stockAvailable = Integer.parseInt(StockAvailableLabel.getText());
        System.out.println(stockAvailable);
        if (stockAvailable < 1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Out of stock, please choose another album");
            alert.showAndWait();
        }
    }

    @FXML
    private void ConfirmOrder(ActionEvent event){
        connection = connectNow.getConnection();
        String quantityRentedText = quantityRented.getText();
        stockAvailable = Integer.parseInt(StockAvailableLabel.getText());
        int quantityRentedNumber = 0;
        try {
            quantityRentedNumber = Integer.parseInt(quantityRentedText);
        }
        catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The rented quantity is empty");
            alert.showAndWait();
            clean();
        }
        if(quantityRentedText.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in ALL DATA");
            alert.showAndWait();
            clean();
        }
        else if (stockAvailable < 1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Out of stock, please choose another album");
            alert.showAndWait();
            clean();
        }
        else if (quantityRentedNumber < 0 || quantityRentedNumber > stockAvailable)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please make sure quantity rented is between 0 and " + stockAvailable);
            alert.showAndWait();
            clean();
        }
        else
        {
            getQuery();
            insert();
            DialogBoxInConfirmRentedAlbumQuantity();
            clean();
        }
    }

    public void setLabel(String albumIDLabel, String albumNameLabel, String albumUnitPriceLabel,
                         String artistLabel, String genreLabel, String  rentalIDLabel,
                         String stockAvailableLabel, String yearOfReleaseLabel)
    {
        AlbumIDLabel.setText(albumIDLabel);
        AlbumNameLabel.setText(albumNameLabel);
        AlbumUnitPriceLabel.setText(albumUnitPriceLabel);
        ArtistLabel.setText(artistLabel);
        GenreLabel.setText(genreLabel);
        RentalIDLabel.setText(rentalIDLabel);
        StockAvailableLabel.setText(stockAvailableLabel);
        YearOfReleaseLabel.setText(yearOfReleaseLabel);
    }


    private void getQuery() {

        query = "INSERT INTO `ALBUM_RENTAL` (`RENTAL_ID`, `ALBUM_ID`, `QUANTITY_ALBUM_RENTED`, `ALBUM_UNIT_PRICE_WHEN_RENTED`) VALUES (?,?,?,?)";

    }

    private void insert(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,RentalIDLabel.getText());
            preparedStatement.setString(2,AlbumIDLabel.getText());
            preparedStatement.setString(3,quantityRented.getText());
            preparedStatement.setString(4,AlbumUnitPriceLabel.getText());
            preparedStatement.execute();
        } catch (SQLException ignored){}
    }

    @FXML
    private void clean(){
        quantityRented.setText(null);
    }

    private void DialogBoxInConfirmRentedAlbumQuantity() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Order Made!");
        alert.setContentText("The new order is made successfully");
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
