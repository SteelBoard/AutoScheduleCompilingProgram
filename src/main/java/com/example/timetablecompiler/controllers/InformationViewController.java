package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.DbSubjectsDataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InformationViewController implements Initializable {

    @FXML private ChoiceBox<String> subjectChoice;
    @FXML private Label subjectLabel, classroomLabel, teachersLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subjectChoice.getItems().add("<Не выбрано>");
        subjectChoice.getItems().addAll(DbSubjectsDataModel.getQuantityOfSubjects().keySet());
        HashMap<String, Integer> classrooms = DbSubjectsDataModel.getClassrooms();
        HashMap<String, ArrayList<String>> teachers = DbSubjectsDataModel.getTeachersWithSubjects();

        subjectChoice.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue.equals("<Не выбрано>")) {

                subjectLabel.setText("");
                classroomLabel.setText("");
                teachersLabel.setText("");
            }
            else {

                subjectLabel.setText(newValue);
                classroomLabel.setText(classrooms.get(newValue).toString());
                teachersLabel.setText(teachers.get(newValue).get(0) + ", " + teachers.get(newValue).get(1));
            }
        });
    }


}
