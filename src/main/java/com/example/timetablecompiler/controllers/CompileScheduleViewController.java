package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.TimeTableCompilerUltimate;
import com.example.timetablecompiler.model.*;
import com.example.timetablecompiler.model.rules.Rule;
import com.example.timetablecompiler.util.TextFormatingUtil;
import com.example.timetablecompiler.util.Views;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompileScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private Label numberOfRulesLabel;
    @FXML private ChoiceBox<String> operationChoice, gradeChoice;
    private Stage rulesCompilerStage;
    private Schedule currentSchedule;
    private Boolean isRulesCompilerShowing;
    private ArrayList<Rule> rules = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gradeChoice.getItems().add("<Не выбрано>");
        gradeChoice.getItems().addAll(Classes.A.getGrade(), Classes.B.getGrade(), Classes.C.getGrade(), Classes.D.getGrade());
        gradeChoice.setValue("<Не выбрано>");

        operationChoice.getItems().addAll("<Не выбрано>", "Составить", "Загрузить", "Удалить", "Вывести текущее");
        operationChoice.setValue("<Не выбрано>");


        isRulesCompilerShowing = false;
        addWeekdays();
    }

    public void deleteCurrentSchedule() {

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

    public void outputSchedule(Schedule schedule) {

        Platform.runLater(() -> {

            assert currentSchedule != null;
            if (currentSchedule.getLessonArray() != null) {

                for (int i = 2; i < 7; i++) {

                    for (int j = 1; j < 9; j++) {

                        if (currentSchedule.getLessonArray()[i-2][j-1] != null) {

                            gridPane.add(new VBox(new Label(currentSchedule.getLessonArray()[i-2][j-1].getSubject()),
                                    new Label(TextFormatingUtil.formatTeacherName(currentSchedule.getLessonArray()[i-2][j-1].getTeacher())),
                                    new Label(currentSchedule.getLessonArray()[i-2][j-1].getClassroom().toString())), i-1, j);
                        }
                    }
                }
            }
        });
    }

    private void openRulesCompiler() {

        try {

            rulesCompilerStage = new Stage();
            rulesCompilerStage.setResizable(false);
            rulesCompilerStage.setTitle("Rule Compiler");
            try {

                rulesCompilerStage.getIcons().add(new Image(new FileInputStream("Icon/icon.ico")));
            }
            catch (IOException ex) {}
            FXMLLoader loader = new FXMLLoader(TimeTableCompilerUltimate.class.getResource(Views.RULECOMPILE.getFileName()));
            rulesCompilerStage.setScene(new Scene(loader.load()));
            RulesCompilerViewController controller = loader.getController();
            controller.setRulesCompilerStage(rulesCompilerStage);
            controller.setInitialController(this);

            rulesCompilerStage.setOnCloseRequest(windowEvent -> {

                isRulesCompilerShowing = false;
                rulesCompilerStage.close();
            });
            rulesCompilerStage.setAlwaysOnTop(true);
            rulesCompilerStage.initModality(Modality.APPLICATION_MODAL);
            rulesCompilerStage.show();
            isRulesCompilerShowing = true;
        }
        catch (IOException ex) {

            throw new RuntimeException();
        }
    }

    private void createNullGeneratedScheduleAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неудачная попытка");
        alert.setHeaderText("Не удалось сгенерировать расписание.\nИзмените правила или попробуйте ещё раз.");
        alert.showAndWait();
    }
    private void createNotChoosedAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неудачная попытка");
        alert.setHeaderText("Выберите команду и класс и попробуйте ещё раз.");
        alert.showAndWait();
    }
    private void createCantLoadAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неудачная попытка");
        alert.setHeaderText("Невозможно загрузить расписания для данного класса из-за пересечения уроков.\nПопробуйте сгенерировать для данного класса или загрузить для другого.");
        alert.showAndWait();
    }
    public void updateNumberOfRules() {

        numberOfRulesLabel.setText("Добавлено правил: " + rules.size());
    }
    public void setRules(ArrayList<Rule> rules) {

        this.rules = rules;
    }
    public void setIsRulesCompilerShowing(Boolean flag) {

        this.isRulesCompilerShowing = flag;
    }
    public Schedule getCurrentSchedule() {

        return this.currentSchedule;
    }


    @FXML
    private void clickToExecute() {

        if (operationChoice.getValue().equals("<Не выбрано>") || gradeChoice.getValue().equals("<Не выбрано>")) {

            createNotChoosedAlert();
            return;
        }

        Classes currentGrade = Classes.getGradeByString(gradeChoice.getValue());

        switch(operationChoice.getValue()) {

            case "Составить" -> {

                if (currentSchedule != null) {

                    deleteCurrentSchedule();
                }
                currentSchedule = Schedule.generate(rules, currentGrade);
                if (currentSchedule == null) {

                    createNullGeneratedScheduleAlert();
                }
                else {

                    outputSchedule(currentSchedule);
                }
            }
            case "Загрузить" -> {

                if (currentSchedule == null) {

                    return;
                }

                if (!currentSchedule.isScheduleCorrect(currentGrade)) {

                    createCantLoadAlert();
                    return;
                }

                DbScheduleDataModel.deleteSchedule(currentGrade);
                DbScheduleDataModel.insertSchedule(currentSchedule, currentGrade);
            }
            case "Удалить" -> {

                DbScheduleDataModel.deleteSchedule(currentGrade);
            }
            case "Вывести текущее" -> {

                if (currentSchedule != null) {

                    deleteCurrentSchedule();
                }
                currentSchedule = DbScheduleDataModel.getSchedule(currentGrade);
                outputSchedule(currentSchedule);
            }
        }

    }
    @FXML
    private void clickToCompileRules() {

        if (isRulesCompilerShowing) {

            return;
        }
        if (rulesCompilerStage == null) {

            openRulesCompiler();
            return;
        }
        rulesCompilerStage.show();
    }
    @FXML
    private void clickToDeleteRules() {

        rules.clear();
        updateNumberOfRules();
    }
}
