package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.model.Lesson;
import com.example.timetablecompiler.model.ScheduleCompiler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeeingScheduleViewController implements Initializable {

    @FXML private FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<ArrayList<Lesson>> schedule = ScheduleCompiler.compileInitialSchedule();

        Platform.runLater(() -> {

            flowPane.getStylesheets().add(TimeTableCompilerUltimate.class.getResource("css/Schedule.css").toString());
            for (ArrayList<Lesson> lessons : schedule) {

                for (Lesson lesson : lessons) {

                    flowPane.getChildren().add(new VBox(new Label(lesson.getSubject()), new Label(lesson.getTeacher())));
                }
            }
        });
    }
}
