package com.example.timetablecompiler.model;

public enum Classes {

    A("10А"),
    B("10Б");

    private final String grade;

    Classes(String grade) {

        this.grade = grade;
    }

    public String getGrade() {

        return this.grade;
    }

    public static Classes getGradeByString(String string) {

        return switch (string) {

            case "10А" -> A;
            case "10Б" -> B;
            default ->  null;
        };
    }
}
