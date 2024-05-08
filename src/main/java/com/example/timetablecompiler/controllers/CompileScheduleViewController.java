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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompileScheduleViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private Label numberOfRulesLabel;
    private Stage rulesCompilerStage;
    private Schedule currentSchedule;
    private Boolean isRulesCompilerShowing;
    private Boolean isAddingOptionalOpen;
    private ArrayList<Rule> rules = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isRulesCompilerShowing = false;
        isAddingOptionalOpen = false;
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

    private void openAddingOptional() {

        try {

            Stage addingOptionalStage = new Stage();
            FXMLLoader loader = new FXMLLoader(TimeTableCompilerUltimate.class.getResource(Views.ADDINGOPTIONAL.getFileName()));
            addingOptionalStage.setScene(new Scene(loader.load()));
            AddingOptionalViewController controller = loader.getController();
            controller.setInitialController(this);
            controller.setCurrentSchedule(currentSchedule);
            controller.setStage(addingOptionalStage);

            addingOptionalStage.setOnCloseRequest(windowEvent -> {

                isAddingOptionalOpen = false;
                addingOptionalStage.close();
            });
            addingOptionalStage.setAlwaysOnTop(true);
            addingOptionalStage.initModality(Modality.APPLICATION_MODAL);
            addingOptionalStage.show();
            isAddingOptionalOpen = true;
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

    private void createNullCurrentScheduleAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неудачная попытка");
        alert.setHeaderText("Для добавления факультатива необходимо \nсгенерировать расписание.");
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
    public void setIsAddingOptionalOpen(Boolean flag) {

        this.isAddingOptionalOpen = flag;
    }
    public Schedule getCurrentSchedule() {

        return this.currentSchedule;
    }

    @FXML
    private void clickToGenerateSchedule() {

        if (currentSchedule != null) {

            deleteCurrentSchedule();
        }
        currentSchedule = Schedule.generate(rules);
        if (currentSchedule == null) {

            createNullGeneratedScheduleAlert();
        }
        else {

            outputSchedule(currentSchedule);
        }
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
    @FXML
    private void clickToAddOptional() {

        if (isAddingOptionalOpen) {

            return;
        }
        if (currentSchedule == null) {

            createNullCurrentScheduleAlert();
            return;
        }
        openAddingOptional();
    }
    @FXML
    private void clickToOutputA() {

        if (currentSchedule != null) {

            deleteCurrentSchedule();
        }
        currentSchedule = DbScheduleDataModel.getSchedule(Classes.A);
        outputSchedule(currentSchedule);
    }
    @FXML
    private void clickToOutputB() {

        if (currentSchedule != null) {

            deleteCurrentSchedule();
        }
        currentSchedule = DbScheduleDataModel.getSchedule(Classes.B);
        outputSchedule(currentSchedule);
    }
}
