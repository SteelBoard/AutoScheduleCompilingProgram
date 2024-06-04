package com.example.timetablecompiler;

import com.example.timetablecompiler.model.User;
import com.example.timetablecompiler.util.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class TimeTableCompilerUltimate extends Application {

    private static Stage stage;
    private static User currentUser;


    @Override
    public void start(Stage stage) {

        TimeTableCompilerUltimate.stage = stage;
        TimeTableCompilerUltimate.stage.setResizable(false);
        stage.setTitle("Time Table Compiler Ultimate");
        try {

            stage.getIcons().add(new Image(new FileInputStream("Icon/icon.ico")));
        }
        catch (IOException ex) {}
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            stage.centerOnScreen();
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            stage.centerOnScreen();
        });
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

    public static void openInfo() {

        try {

            Stage infoStage = new Stage();
            infoStage.setResizable(false);
            infoStage.setHeight(270);
            infoStage.setWidth(450);
            infoStage.getIcons().add(new Image(new FileInputStream("Icon/icon.ico")));
            FlowPane pane = new FlowPane(new ImageView(new Image(new FileInputStream("Icon/icon.ico"))));
            pane.setHgap(6);
            pane.getChildren().add(new Label("""
                    Название: TimeTableCompiler
                    
                    Назначение: Организация занятий 
                    для 10-ых классов
                    
                    Версия: 1.0.0
                    
                    Автор: Беляев В.Д.
                    
                    Руководитель: Заельская Н.А.
                    """));
            infoStage.setScene(new Scene(pane));
            infoStage.initModality(Modality.APPLICATION_MODAL);
            infoStage.show();
        }
        catch (IOException ex) {

            ex.printStackTrace();
            return;
        }
    }
}