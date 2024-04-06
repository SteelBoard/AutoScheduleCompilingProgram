package com.example.timetablecompiler.model;

public enum Classes {

    A("10А"),
    B("10А");

    private final String grade;

    Classes(String grade) {

        this.grade = grade;
    }

    public String getGrade() {

        return this.grade;
    }
}
