package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.model.DbRegistrationLoginModel;
import com.example.timetablecompiler.util.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label dataWrongLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    private void clickToLogin() {

        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {

            dataWrongLabel.setText("Одно из полей пустое");
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
}
