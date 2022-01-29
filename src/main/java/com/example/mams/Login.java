package com.example.mams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private Button button , button2;

    @FXML
    private TextField EmailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }

    String Email;
    String Password;
    //    default Email and Password
    //    Email : mams@gmail.com
    //    Password: cat201
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setCursor(Cursor.HAND);
        button2.setCursor(Cursor.HAND);
    }

    @FXML
    public void goToHomePage (ActionEvent event)
    {
        Email = EmailInput.getText();
        Password = passwordInput.getText();

        if (!Objects.equals(Email, "mams@gmail.com" )&& !Objects.equals(Password, "cat201"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Invalid Email and Password. Please reenter email and password.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty()) {
                System.out.println("Alert closed");
            } else if (result.get() == ButtonType.OK) {
                System.out.println("OK!");
            } else if (result.get() == ButtonType.CANCEL) {
                System.out.println("Never!");
            }
            clear();
        }
        else {
            if(!Objects.equals(Email, "mams@gmail.com")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Invalid Email. Please renter the email");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isEmpty()) {
                    System.out.println("Alert closed");
                } else if (result.get() == ButtonType.OK) {
                    System.out.println("OK!");
                } else if (result.get() == ButtonType.CANCEL) {
                    System.out.println("Never!");
                }
                clear();
            }
            else if (!Objects.equals(Password, "cat201")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Invalid Password.Please reenter the password");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isEmpty()) {
                    System.out.println("Alert closed");
                } else if (result.get() == ButtonType.OK) {
                    System.out.println("OK!");
                } else if (result.get() == ButtonType.CANCEL) {
                    System.out.println("Never!");
                }
                clear();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Congratulations");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isEmpty()) {
                    System.out.println("Alert closed");
                } else if (result.get() == ButtonType.OK) {
                    System.out.println("OK!");
                } else if (result.get() == ButtonType.CANCEL) {
                    System.out.println("Never!");
                }

            }
        }



    }
    public void clear()
    {
        EmailInput.setText(null);
        passwordInput.setText(null);
    }
}


