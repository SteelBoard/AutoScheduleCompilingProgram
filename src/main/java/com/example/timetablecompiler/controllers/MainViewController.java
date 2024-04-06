package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.util.Views;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML private GridPane framePane;

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
}
