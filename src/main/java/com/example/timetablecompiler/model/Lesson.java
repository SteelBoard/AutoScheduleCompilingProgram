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

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Lesson)) {

            return false;
        }

        Lesson otherLesson = (Lesson) obj;
        return this.subject.equals(otherLesson.getSubject()) &&
                this.classroom.equals(otherLesson.classroom) &&
                this.teacher.equals(otherLesson.teacher);
    }

    public boolean isCross(Lesson otherLesson) {

        return this.subject.equals(otherLesson.getSubject()) || this.teacher.equals(otherLesson.getTeacher());
    }
}
