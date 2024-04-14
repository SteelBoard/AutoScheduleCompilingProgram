package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.*;
import com.example.timetablecompiler.util.TextFormatingUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompileScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;
    private Schedule currentSchedule;
    private Boolean isRulesCompilerShowing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addWeekdays();
    }

    @FXML
    private void clickToGenerateSchedule() {

        deleteCurrentSchedule();
        currentSchedule = Schedule.generate(new ArrayList<>());

        Platform.runLater(() -> {

            assert currentSchedule != null;
            if (currentSchedule.getLessonArray() != null) {

                for (int i = 2; i < 7; i++) {

                    for (int j = 1; j < 9; j++) {

                        if (currentSchedule.getLessonArray()[i-2][j-1] != null) {

                            gridPane.add(new VBox(new Label(currentSchedule.getLessonArray()[i-2][j-1].getSubject()),
                                    new Label(TextFormatingUtil.formatTeacherName(currentSchedule.getLessonArray()[i-2][j-1].getTeacher())),
                                    new Label(currentSchedule.getLessonArray()[i-2][j-1].getClassroom().toString())), i-1, j+1);
                        }
                    }
                }
            }
        });
    }

    @FXML
    private void clickToLoadForA() {

        if (currentSchedule == null) {

            return;
        }

        DbScheduleDataModel.deleteSchedule(Classes.A);
        DbScheduleDataModel.insertSchedule(currentSchedule, Classes.A);
    }

    @FXML
    private void clickToLoadForB() {

        if (currentSchedule == null) {

            return;
        }

        DbScheduleDataModel.deleteSchedule(Classes.B);
        DbScheduleDataModel.insertSchedule(currentSchedule, Classes.B);
    }

    @FXML
    private void clickToDeleteForA() {

        DbScheduleDataModel.deleteSchedule(Classes.A);
    }

    @FXML
    private void clickToDeleteForB() {

        DbScheduleDataModel.deleteSchedule(Classes.B);
    }

    public void deleteCurrentSchedule() {

        Platform.runLater(() -> {

            gridPane.getChildren().removeIf(node ->
                    (GridPane.getColumnIndex(node) >= 1 && GridPane.getColumnIndex(node) <= 6) &&
                            (GridPane.getRowIndex(node) >= 2 && GridPane.getRowIndex(node) <= 9));
        });
    }

    public void addWeekdays() {

        Platform.runLater(() -> {

            for (int i = 1; i < 6; i++) {

                gridPane.add(new Label(Weekdays.getWeekdayByNumber(i).getName()), i, 1);
            }

            for (int i = 2; i < 10; i++) {

                gridPane.add(new Label(LessonTimes.getLessonTimeByNumber(i-1).getTime()), 0, i);
            }
        });
    }

    public void openRulesCompiler() {

        Stage rulesCompilerStage = new Stage();

    }
}
