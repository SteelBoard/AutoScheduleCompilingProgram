package com.example.timetablecompiler.controllers;

import com.example.timetablecompiler.model.DbSubjectsDataModel;
import com.example.timetablecompiler.model.LessonTimes;
import com.example.timetablecompiler.model.Weekdays;
import com.example.timetablecompiler.model.rules.PositionRule;
import com.example.timetablecompiler.model.rules.Rule;
import com.example.timetablecompiler.model.rules.SequenceRule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RulesCompilerViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private Label wrongDataLabel;
    private CompileScheduleViewController initialController;
    private Stage rulesCompilerStage;
    private ObservableList<ChoiceBox<String>> rules;
    private ArrayList<Rule> chosenRules = new ArrayList<>();
    private Integer[] columnCounts = new Integer[6];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rules = FXCollections.observableArrayList(gridPane.getChildren().stream()
                .filter(node -> node instanceof ChoiceBox && ((ChoiceBox<?>) node).getItems().isEmpty())
                .map(node -> {
                    if (((ChoiceBox<?>) node).getItems().isEmpty()) {
                        return (ChoiceBox<String>) node;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
        for (int i = 0; i < 6; i++) {

            columnCounts[i] = 1;
        }

        for (ChoiceBox<String> choiceBox : rules) {

            choiceBox.getItems().addAll("<Не выбрано>", "Последовательность", "Позиция");
            choiceBox.setValue("<Не выбрано>");

            choiceBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {

                gridPane.getChildren().removeIf(node -> Objects.equals(GridPane.getRowIndex(choiceBox), GridPane.getRowIndex(node))
                        && node != choiceBox);

                columnCounts[GridPane.getRowIndex(choiceBox)] = 1;
                HashMap<String, Integer> subjects = DbSubjectsDataModel.getQuantityOfSubjects();

                if (newValue.equals("<Не выбрано>")) {

                }
                else if (newValue.equals("Последовательность")) {

                    for (int i = 0; i < 3; i++) {

                        ChoiceBox<String> subjectChoice = new ChoiceBox<>();
                        subjectChoice.getItems().add("<Не выбрано>");
                        subjectChoice.getItems().addAll(subjects.keySet());
                        subjectChoice.setValue("<Не выбрано>");
                        gridPane.add(subjectChoice, columnCounts[GridPane.getRowIndex(choiceBox)], GridPane.getRowIndex(choiceBox));
                        columnCounts[GridPane.getRowIndex(choiceBox)]++;
                    }
                }
                else if (newValue.equals("Позиция")) { // Заменить на switch case

                    ChoiceBox<String> day, lesson, subject;

                    day = new ChoiceBox<>();
                    day.getItems().add("<Не выбрано>");
                    for (Weekdays weekday : Weekdays.getAllWeekdays()) {

                        day.getItems().add(weekday.getName());
                    }
                    day.setValue("<Не выбрано>");
                    gridPane.add(day, columnCounts[GridPane.getRowIndex(choiceBox)], GridPane.getRowIndex(choiceBox));
                    columnCounts[GridPane.getRowIndex(choiceBox)]++;

                    lesson = new ChoiceBox<>();
                    lesson.getItems().add("<Не выбрано>");
                    for (LessonTimes lessonTime : LessonTimes.getAllLessonTimesNumber()) {

                        lesson.getItems().add(lessonTime.getNumber().toString());
                    }
                    lesson.setValue("<Не выбрано>");
                    gridPane.add(lesson, columnCounts[GridPane.getRowIndex(choiceBox)], GridPane.getRowIndex(choiceBox));
                    columnCounts[GridPane.getRowIndex(choiceBox)]++;

                    subject = new ChoiceBox<>();
                    subject.getItems().add("<Не выбрано>");
                    subject.getItems().addAll(subjects.keySet());
                    subject.setValue("<Не выбрано>");
                    gridPane.add(subject, columnCounts[GridPane.getRowIndex(choiceBox)], GridPane.getRowIndex(choiceBox));
                    columnCounts[GridPane.getRowIndex(choiceBox)]++;
                }
            });
        }
    }

    private Boolean checkRules() {

        boolean result = true;
        for (ChoiceBox<String> choice: rules) {

            AtomicInteger count = new AtomicInteger();
            switch (choice.getValue()) {

                case "Последовательность" -> {

                    gridPane.getChildren().forEach(node -> {

                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);

                        if (columnIndex != null && rowIndex != null && Objects.equals(rowIndex, GridPane.getRowIndex(choice))
                                && columnIndex > 0
                                && node instanceof ChoiceBox<?> ) {

                            ChoiceBox<String> subjectChoice = (ChoiceBox<String>) node;
                            if (!subjectChoice.getValue().equals("<Не выбрано>")) {

                                count.getAndIncrement();
                            }
                        }
                    });

                    result = result && count.get() > 1;
                }
                case "Позиция" -> {

                    for (Node node : gridPane.getChildren()) {

                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);

                        if (columnIndex != null && rowIndex != null && Objects.equals(rowIndex, GridPane.getRowIndex(choice))
                                && columnIndex > 0
                                && node instanceof ChoiceBox<?> ) {

                            ChoiceBox<String> positionRuleChoiceBox = (ChoiceBox<String>) node;
                            result = result && !positionRuleChoiceBox.getValue().equals("<Не выбрано>");
                        }
                    }
                }
            }
            count.set(0);
            if (!result) {

                return result;
            }
        }

        return true;
    }

    // Написано только для одного типа правил
    @FXML
    private void clickToSaveRules() {

        if (!checkRules()) {

            wrongDataLabel.setText("Неправильно введённые данные!");
            return;
        }

        chosenRules.clear();
        for (ChoiceBox<String> choice : rules) {

            switch (choice.getValue()) {

                case "Последовательность" -> {

                    ArrayList<String> subjectSequence = new ArrayList<>();
                    gridPane.getChildren().forEach(node -> {

                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);
                        if (columnIndex != null && rowIndex != null && Objects.equals(rowIndex, GridPane.getRowIndex(choice))
                                && columnIndex > 0 && columnIndex < 5
                                && node instanceof ChoiceBox<?>) {

                            ChoiceBox<String> subject = (ChoiceBox<String>) node;
                            subjectSequence.add(subject.getValue());
                        }
                    });
                    chosenRules.add(new SequenceRule(subjectSequence));
                }
                case "Позиция" -> {

                    Weekdays day = null;
                    LessonTimes lesson = null;
                    String subject = null;
                    for (Node node : gridPane.getChildren()) {

                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);
                        if (columnIndex != null && rowIndex != null && Objects.equals(rowIndex, GridPane.getRowIndex(choice))
                                && columnIndex > 0 && columnIndex < 5
                                && node instanceof ChoiceBox<?>) {

                            ChoiceBox<String> positionRuleChoiceBox = (ChoiceBox<String>) node;
                            if (positionRuleChoiceBox.getItems().contains("Понедельник")) {

                                day = Weekdays.getWeekdayByName(positionRuleChoiceBox.getValue());
                            } else if (positionRuleChoiceBox.getItems().contains("1")) {

                                lesson = LessonTimes.getLessonTimeByNumber(Integer.parseInt(positionRuleChoiceBox.getValue()));
                            } else if (positionRuleChoiceBox.getItems().contains("Алгебра")) {

                                subject = positionRuleChoiceBox.getValue();
                            }
                        }
                    }
                    chosenRules.add(new PositionRule(day, lesson, subject));
                }
            }
        }
        initialController.setRules(chosenRules);
        initialController.setIsRulesCompilerShowing(false);
        initialController.updateNumberOfRules();
        rulesCompilerStage.close();
    }

    public ArrayList<Rule> getCurrentRules() {

        return this.chosenRules;
    }
    public void setRulesCompilerStage(Stage stage) {

        this.rulesCompilerStage = stage;
    }
    public void setInitialController(CompileScheduleViewController controller) {

        this.initialController = controller;
    }
}
