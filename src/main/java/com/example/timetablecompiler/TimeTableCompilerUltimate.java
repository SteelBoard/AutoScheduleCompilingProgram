package com.example.timetablecompiler;

import com.example.timetablecompiler.model.User;
import com.example.timetablecompiler.util.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// базу данных посмотреть
public class TimeTableCompilerUltimate extends Application {

    private static Stage stage;
    private static User currentUser;

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) {

        TimeTableCompilerUltimate.stage = stage;
        stage.setTitle("Time Table Compiler Ultimate");
        switchToScene(Views.LOGIN);

        stage.show();
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

    public static Stage getInstanceOfStage() {

        return stage;
    }

    public static User getCurrentUser() {

        return currentUser;
    }

    public static void setCurrentUser(User user) {

        currentUser = user;
    }
}