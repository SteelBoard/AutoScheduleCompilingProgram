package com.example.timetablecompiler;

import com.example.timetablecompiler.model.Lesson;
import com.example.timetablecompiler.model.ScheduleCompiler;
import com.example.timetablecompiler.util.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class TimeTableCompilerUltimate extends Application {

    private static Stage stage;

    public static Stage getInstanceOfStage() {

        return TimeTableCompilerUltimate.stage;
    }

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) throws IOException {

        TimeTableCompilerUltimate.stage = stage;
        stage.setTitle("Time Table Compiler Ultimate");
        switchToScene(Views.LOGIN);
        stage.show();
        for (ArrayList<Lesson> array : ScheduleCompiler.compileInitialSchedule()) {

            System.out.println(array);
        }
    }

    public static void switchToScene(Views view) {

        try {

            FXMLLoader loader = new FXMLLoader(TimeTableCompilerUltimate.class.getResource(view.getFileName()));
            TimeTableCompilerUltimate.getInstanceOfStage().setScene(new Scene(loader.load()));
        }
        catch (Exception ex) {

            throw new RuntimeException();
        }
    }
}