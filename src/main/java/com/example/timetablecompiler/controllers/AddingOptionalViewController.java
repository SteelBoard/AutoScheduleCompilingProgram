package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AddingOptionalViewController implements Initializable {

    @FXML private TextField name;
    @FXML private ChoiceBox<String> weekDay, lessonNumber, teacher, classroom;
    private Stage stage;
    private CompileScheduleViewController initialController;
    private Schedule currentSchedule;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lessonNumber.getItems().add("<Не выбрано>");
        weekDay.getItems().addAll("<Не выбрано>", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница");
        weekDay.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            lessonNumber.getItems().clear();
            lessonNumber.getItems().add("<Не выбрано>");
            if (newValue.equals("<Не выбрано>")) {

                return;
            }
            for (int i = 0; i < 8; i++) {

                 if (currentSchedule.getLessonArray()[Weekdays.getWeekdayByName(newValue).getNumber()-1][i] == null) {

                     lessonNumber.getItems().add(Integer.toString(i+1));
                 }
            }
        });

        teacher.getItems().add("<Не выбрано>");
        teacher.getItems().addAll(DbSubjectsDataModel.getSubjectWithTeachers().values());
        classroom.getItems().add("<Не выбрано>");
        for (int i = 0; i < 30; i++) {

            classroom.getItems().add(Integer.toString(i+1));
        }

        weekDay.setValue("<Не выбрано>");
        lessonNumber.setValue("<Не выбрано>");
        teacher.setValue("<Не выбрано>");
        classroom.setValue("<Не выбрано>");
    }

    private Boolean checkFields() {

        return !name.getText().isEmpty() && weekDay.getValue() != null && !weekDay.getValue().equals("<Не выбрано>")
                && lessonNumber.getValue() != null && !lessonNumber.getValue().equals("<Не выбрано>") &&
                teacher.getValue() != null && !teacher.getValue().equals("<Не выбрано>") &&
                classroom.getValue() != null && !classroom.getValue().equals("<Не выбрано>");
    }
    @FXML
    private void clickToSaveOptional() {

        if (!checkFields()) {

            return;
        }

        currentSchedule.getLessonArray()[Weekdays.getWeekdayByName(weekDay.getValue()).getNumber()-1][Integer.parseInt(lessonNumber.getValue())-1]
                = new Lesson(name.getText(), teacher.getValue(), Integer.parseInt(classroom.getValue()));

        initialController.setIsAddingOptionalOpen(false);
        initialController.deleteCurrentSchedule();
        initialController.outputSchedule(initialController.getCurrentSchedule());
        stage.close();
    }

    public void setCurrentSchedule(Schedule schedule) {

        this.currentSchedule = schedule;
    }
    public void setInitialController(CompileScheduleViewController controller) {

        this.initialController = controller;
    }
    public void setStage(Stage stage) {

        this.stage = stage;
    }
}
