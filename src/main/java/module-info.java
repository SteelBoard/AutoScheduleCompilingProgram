module com.example.timetablecompiler {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jbcrypt;

    opens com.example.timetablecompiler to javafx.fxml;
    exports com.example.timetablecompiler;
    exports com.example.timetablecompiler.util;
    opens com.example.timetablecompiler.util to javafx.fxml;
    exports com.example.timetablecompiler.controllers;
    opens com.example.timetablecompiler.controllers to javafx.fxml;
}