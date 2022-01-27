package com.example.mams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class AlbumStock implements Initializable {


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
    @FXML
    private TableColumn<MusicAlbum, String> button_col;
    @FXML
    private Button music, refreshButton;

    String query = null;
    Connection connection = null;
    PreparedStatement prepareStatement = null;
    ResultSet resultSet = null;
    DatabaseConnection connectNow = new DatabaseConnection();
    MusicAlbum musicAlbum = null;
    private boolean update;
    int albumID;

    ObservableList<MusicAlbum> MusicAlbumList = FXCollections.observableArrayList();

    public void loadData(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try{
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM ALBUM");

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

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            /*
                            try {
                                musicAlbum = AlbumTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `student` WHERE id  ="+musicAlbum.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            */

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            /*
                            musicAlbum = AlbumTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/tableView/addStudent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddNewAlbum addNewAlbum = loader.getController();
                            addNewAlbum.setUpdate(true);
                            addNewAlbum.setTextField(musicAlbum.getAlbumName(), musicAlbum.getArtist(), musicAlbum.getGenre(),musicAlbum.getYearOfRelease(), musicAlbum.getQuantityOnHand(), musicAlbum.getAlbumUnitPrice());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            */
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        button_col.setCellFactory(cellFactory);
        AlbumTable.setItems(MusicAlbumList);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        music.setCursor(Cursor.HAND);
        refreshButton.setCursor(Cursor.HAND);
        loadData();
    }


    @FXML
    private void addNewAlbum(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addNewAlbum.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    private void refreshTable(MouseEvent event){
        MusicAlbumList.clear();
        loadData();
    }

}
