package com.example.timetablecompiler.model;

public class Lesson {

    private String subject, teacher;
    private Integer classroom;

    public Lesson(String subject, String teacher, Integer classroom) {

        this.subject = subject;
        this.teacher = teacher;
        this.classroom = classroom;
    }
    public Lesson() {

    }

    @Override
    public String toString() {

        return this.subject;
    }

    public String getSubject() {

        return subject;
    }
    public void setSubject(String subject) {

        this.subject = subject;
    }
    public String getTeacher() {

        return teacher;
    }
    public void setTeacher(String teacher) {

        this.teacher = teacher;
    }
    public Integer getClassroom() {

        return classroom;
    }
    public void setClassroom(Integer classroom) {

        this.classroom = classroom;
    }
}
