package com.example.mams;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewAlbum implements Initializable {

    @FXML
    private TextField AlbumNameInput;

    @FXML
    private TextField ArtistInput;

    @FXML
    private TextField GenreInput;

    @FXML
    private TextField YearOfReleaseInput;

    @FXML
    private TextField AlbumQuantityInput;

    @FXML
    private TextField AlbumUnitPriceInput;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    DatabaseConnection connectNow = new DatabaseConnection();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addNewAlbum(MouseEvent event){
        connection = connectNow.getConnection();
        String AlbumName = AlbumNameInput.getText();
        String Artist = ArtistInput.getText();
        String Genre = GenreInput.getText();
        String YearOfReleaseText = YearOfReleaseInput.getText();
        String AlbumQuantityText  =AlbumQuantityInput.getText();
        String AlbumUnitPriceText = AlbumUnitPriceInput.getText();
        int YearOfRelease =  Integer.parseInt(YearOfReleaseText);
        int  AlbumQuantity = Integer.parseInt(AlbumQuantityText);
        double AlbumUnitPrice = Double.parseDouble(AlbumUnitPriceText);

        if(AlbumName.isEmpty()|| Artist.isEmpty() ||
                Genre.isEmpty() || YearOfReleaseText.isEmpty() ||
                AlbumQuantityText.isEmpty()|| AlbumUnitPriceText.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("PLEASE FILL IN ALL DATA");
            alert.showAndWait();
        }
        else
        {
            getQuery();
            insert();
            clean();
        }
    }

    private void getQuery() {
        query = "INSERT INTO `ALBUM` (`ALBUM_NAME`, `ARTIST`, `GENRE`, `YEAR_OF_RELEASE`, `QUANTITY_ON_HAND`, `ALBUM_UNIT_PRICE`) VALUES (?,?,?,?,?,?)";
    }

    private void insert(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,AlbumNameInput.getText());
            preparedStatement.setString(2,ArtistInput.getText());
            preparedStatement.setString(3,GenreInput.getText());
            preparedStatement.setString(4,YearOfReleaseInput.getText());
            preparedStatement.setString(5,AlbumQuantityInput.getText());
            preparedStatement.setString(6,AlbumUnitPriceInput.getText());
            preparedStatement.execute();
        } catch (SQLException ignored){}
    }

    @FXML
    private void clean(){
       AlbumNameInput.setText(null);
       ArtistInput.setText(null);
       GenreInput.setText(null);
       YearOfReleaseInput.setText(null);
       AlbumQuantityInput.setText(null);
       AlbumUnitPriceInput.setText(null);
    }


}
