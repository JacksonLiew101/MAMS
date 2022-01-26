package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumStock {
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

    ObservableList<MusicAlbum> oblist = FXCollections.observableArrayList();
    public void connectButton (ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM ALBUM");

            while(rs.next()) {
                //columnLabel is referring to the SQL table column label
                oblist.add(new MusicAlbum(rs.getInt("ALBUM_ID"),
                        rs.getString("ALBUM_NAME"),
                        rs.getString("ARTIST"),
                        rs.getString("GENRE"),
                        rs.getInt("YEAR_OF_RELEASE"),
                        rs.getInt("QUANTITY_ON_HAND"),
                        rs.getInt("ALBUM_UNIT_PRICE") ));

            }
        } catch (SQLException ignored){

        }
        //value here are referring to the attribute of class MusicAlbum
        albumID_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("AlbumID"));
        albumName_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("AlbumName"));
        artist_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("Artist"));
        genre_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,String>("Genre"));
        yearOfRelease_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("YearOfRelease"));
        quantityOnHand_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("QuantityOnHand"));
        albumUnitPrice_col.setCellValueFactory(new PropertyValueFactory<MusicAlbum,Integer>("AlbumUnitPrice"));
        AlbumTable.setItems(oblist);

    }
}
