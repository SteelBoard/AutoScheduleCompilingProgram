package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.model.Lesson;
import com.example.timetablecompiler.model.Schedule;
import com.example.timetablecompiler.model.ScheduleCompiler;
import com.example.timetablecompiler.util.TextFormatingUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeeingScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Schedule schedule = ScheduleCompiler.compileFullSchedule(new ArrayList<>());

        Platform.runLater(() -> {

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
