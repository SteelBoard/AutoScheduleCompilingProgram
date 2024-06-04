package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.DbSubjectsDataModel;
import com.example.timetablecompiler.util.TextFormatingUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InformationViewController implements Initializable {

    @FXML private ChoiceBox<String> subjectChoice, teacherChoice;
    @FXML private Label ss_subjectLabel, ss_classroomLabel, ss_teachersLabel, ts_teacherLabel, ts_subjectsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subjectChoice.getItems().add("<Не выбрано>");
        subjectChoice.getItems().addAll(DbSubjectsDataModel.getQuantityOfSubjects().keySet());
        subjectChoice.setValue("<Не выбрано>");
        HashMap<String, Integer> classrooms = DbSubjectsDataModel.getClassrooms();
        HashMap<String, ArrayList<String>> teachers = DbSubjectsDataModel.getSubjectsWithTeachers();

        subjectChoice.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue.equals("<Не выбрано>")) {

                ss_subjectLabel.setText("");
                ss_classroomLabel.setText("");
                ss_teachersLabel.setText("");
            }
            else {

                ss_subjectLabel.setText(newValue);
                ss_classroomLabel.setText(classrooms.get(newValue).toString());
                ss_teachersLabel.setText(teachers.get(newValue).get(0) + ", " + teachers.get(newValue).get(1));
            }
        });

        HashMap<String, ArrayList<String>> teachersWithSubjectsMap = DbSubjectsDataModel.getTeachersWithSubjects();
        HashMap<String, String> teachersNames = new HashMap<>();
        for (String name : teachersWithSubjectsMap.keySet()) {

            teachersNames.put(TextFormatingUtil.formatTeacherName(name), name);
        }
        teacherChoice.getItems().add("<Не выбрано>");
        teacherChoice.getItems().addAll(teachersNames.keySet());
        teacherChoice.setValue("<Не выбрано>");

        teacherChoice.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            if (teacherChoice.getValue().equals("<Не выбрано>")) {

                ts_subjectsLabel.setText("");
                ts_teacherLabel.setText("");
            }
            else {

                ts_teacherLabel.setText(teachersNames.get(teacherChoice.getValue()));
                try {

                    ts_subjectsLabel.setText(teachersWithSubjectsMap.get(teachersNames.get(teacherChoice.getValue())).get(0));
                    if (teachersWithSubjectsMap.get(teachersNames.get(teacherChoice.getValue())).size() > 1) {

                        ts_subjectsLabel.setText(ts_subjectsLabel.getText() + ", " + teachersWithSubjectsMap.get(teachersNames.get(teacherChoice.getValue())).get(1));
                    }
                }
                catch (IndexOutOfBoundsException ex) {

                }

            }
        });
    }
}
