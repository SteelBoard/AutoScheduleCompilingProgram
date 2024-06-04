package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.model.DbRegistrationLoginModel;
import com.example.timetablecompiler.util.Views;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label dataWrongLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if (newValue.length() > 25) {

                loginField.setText(oldValue);
            }
        }));

        passwordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if (newValue.length() > 25) {

                passwordField.setText(oldValue);
            }
        }));
    }

    @FXML
    private void clickToLogin() {

        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {

            dataWrongLabel.setText("Поля пусты");
        }
        else if (!DbRegistrationLoginModel.isUserExist(login)) {

            dataWrongLabel.setText("Такого пользователя не существует");
        }
        else if (!DbRegistrationLoginModel.isPasswordCorrect(login, password)) {

            dataWrongLabel.setText("Неправильный пароль");
        }
        else {

            dataWrongLabel.setText("");
            TimeTableCompilerUltimate.setCurrentUser(DbRegistrationLoginModel.getUser(login));
            TimeTableCompilerUltimate.switchToScene(Views.MAIN);
        }
    }

    @FXML
    private void clickToRegistrationWindow() {

        TimeTableCompilerUltimate.switchToScene(Views.REGISTRATION);
    }

    @FXML
    private void clickToQuit() {

        TimeTableCompilerUltimate.getInstanceOfStage().close();
    }

    @FXML
    private void clickToOpenInfo() {

        TimeTableCompilerUltimate.openInfo();
    }
}
