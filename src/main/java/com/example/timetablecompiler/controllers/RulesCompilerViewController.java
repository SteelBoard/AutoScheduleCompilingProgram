package com.example.timetablecompiler.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RulesCompilerViewController implements Initializable {

    @FXML private GridPane gridPane;
    private ArrayList<ChoiceBox<String>> rules;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
