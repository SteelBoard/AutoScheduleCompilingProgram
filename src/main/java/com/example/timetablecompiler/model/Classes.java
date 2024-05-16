package com.example.timetablecompiler.model;

public enum Classes {

    A("10А"),
    B("10Б"),
    C("10В"),
    D("10Г");

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
            case "10В" -> C;
            case "10Г" -> D;
            default ->  null;
        };
    }

    @Override
    public String toString() {

        return this.grade;
    }
}
