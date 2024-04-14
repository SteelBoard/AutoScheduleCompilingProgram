package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.util.Views;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class MainViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private GridPane framePane;
    private Button selectedButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void switchToFrame(Views view) {

        try {

            FXMLLoader loader = new FXMLLoader(TimeTableCompilerUltimate.class.getResource(view.getFileName()));
            framePane.getChildren().setAll((Node) loader.load());
        }
        catch (Exception ex) {

            throw new RuntimeException();
        }
    }



    @FXML
    private void clickToSeeSchedule() {

        switchToFrame(Views.SEEINGSCHEDULE);
    }

    @FXML
    private void clickToCompileSchedule() {

        switchToFrame(Views.COMPILESCHEDULE);
    }

    @FXML
    private void clickToLogOut() {

        TimeTableCompilerUltimate.setCurrentUser(null);
        TimeTableCompilerUltimate.switchToScene(Views.LOGIN);
    }

    @FXML
    private void clickToQuit() {

        TimeTableCompilerUltimate.getInstanceOfStage().close();
    }
}
