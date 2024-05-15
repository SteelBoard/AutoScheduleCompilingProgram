package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.*;
import com.example.timetablecompiler.util.TextFormatingUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SeeingScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private ChoiceBox<String> classChoice;
    private Schedule currentSchedule;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addWeekdays();

        classChoice.getItems().addAll("10А", "10Б", "10В", "10Г");
        classChoice.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            currentSchedule = DbScheduleDataModel.getSchedule(Classes.getGradeByString(newValue));
            outputSchedule();
        });
    }

    private void outputSchedule() {

        deleteCurrentSchedule();
        Platform.runLater(() -> {

            if (currentSchedule.getLessonArray() != null) {

                for (int i = 0; i < 5; i++) {

                    for (int j = 0; j < 8; j++) {

                        if (currentSchedule.getLessonArray()[i][j] != null) {

                            gridPane.add(new VBox(new Label(currentSchedule.getLessonArray()[i][j].getSubject()),
                                    new Label(TextFormatingUtil.formatTeacherName(currentSchedule.getLessonArray()[i][j].getTeacher())),
                                    new Label(currentSchedule.getLessonArray()[i][j].getClassroom().toString())), i+1, j+1);
                        }
                    }
                }
            }
        });
    }

    private void deleteCurrentSchedule() {

        Platform.runLater(() -> {

            gridPane.getChildren().removeIf(node -> {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                return (columnIndex != null && columnIndex >= 1 && columnIndex <= 6) &&
                        (rowIndex != null && rowIndex >= 1 && rowIndex <= 8);
            });
        });
    }

    private void addWeekdays() {

        Platform.runLater(() -> {

            for (int i = 1; i < 6; i++) {

                gridPane.add(new Label(Weekdays.getWeekdayByNumber(i).getName()), i, 0);
            }

            for (int i = 1; i < 9; i++) {

                gridPane.add(new Label(LessonTimes.getLessonTimeByNumber(i).getTime()), 0, i);
            }
        });
    }
}
