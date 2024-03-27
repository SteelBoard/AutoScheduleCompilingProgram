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

public class RegistrationViewController implements Initializable {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label dataWrongLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    private void clickToRegistry() {

        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {

            dataWrongLabel.setText("Одно из полей пустое");
        }
        else if (DbRegistrationLoginModel.isUserExist(login)) {

            dataWrongLabel.setText("Такой пользователь уже существует");
        }
        else {

            DbRegistrationLoginModel.registryUser(login, password);
            dataWrongLabel.setText("");
            TimeTableCompilerUltimate.switchToScene(Views.MAIN);
        }
    }

    @FXML
    private void clickToLoginWindow() {

        TimeTableCompilerUltimate.switchToScene(Views.LOGIN);
    }
}
