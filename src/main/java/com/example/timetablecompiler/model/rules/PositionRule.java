package com.example.timetablecompiler.model.rules;

import com.example.timetablecompiler.model.LessonTimes;
import com.example.timetablecompiler.model.Schedule;
import com.example.timetablecompiler.model.Weekdays;

public class PositionRule extends Rule {

    private Weekdays day;
    private LessonTimes lessonNumber;
    private String subject;

    public PositionRule(Weekdays day, LessonTimes number, String subject) {

        this.day = day;
        this.lessonNumber = number;
        this.subject = subject;
    }

    @Override
    public boolean check(Schedule schedule) {

        return schedule.getLessonArray()[getDay().getNumber()-1][getLessonNumber().getNumber()-1].getSubject().equals(getSubject());
    }


    public Weekdays getDay() {

        return this.day;
    }

    public LessonTimes getLessonNumber() {

        return this.lessonNumber;
    }

    public String getSubject() {

        return this.subject;
    }
}
