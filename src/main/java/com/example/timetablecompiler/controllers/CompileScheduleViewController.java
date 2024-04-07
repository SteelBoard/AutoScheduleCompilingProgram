package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.DbScheduleDataModel;
import com.example.timetablecompiler.model.Schedule;
import com.example.timetablecompiler.util.TextFormatingUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompileScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    private void clickToGenerateSchedule() {

        Schedule schedule = Schedule.generate(new ArrayList<>());

        Platform.runLater(() -> {

            assert schedule != null;
            if (schedule.getLessonArray() != null) {

                for (int i = 2; i < 7; i++) {

                    for (int j = 1; j < 9; j++) {

                        if (schedule.getLessonArray()[i-2][j-1] != null) {

                            gridPane.add(new VBox(new Label(schedule.getLessonArray()[i-2][j-1].getSubject()),
                                    new Label(TextFormatingUtil.formatTeacherName(schedule.getLessonArray()[i-2][j-1].getTeacher()))), i-1, j+1);
                        }
                    }
                }
            }
        });
    }
}
