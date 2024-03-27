package com.example.timetablecompiler.util;

public enum Views {
    LOGIN ("LoginView.fxml"),
    REGISTRATION("RegistrationView.fxml"),
    MAIN("MainView.fxml"),
    SEEINGSCHEDULE("SeeingScheduleView.fxml");

    private String fileName;

    Views(String fileName) {

        this.fileName = fileName;
    }

    public String getFileName() { return this.fileName; }
}
