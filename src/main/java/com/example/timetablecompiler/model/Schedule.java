package com.example.timetablecompiler.model;

import com.example.timetablecompiler.model.rules.Rule;

import java.util.ArrayList;

public class Schedule {

    private Lesson[][] lessonArray;

    public Schedule(Lesson[][] lessonList) {

        this.lessonArray = lessonList;
    }

    public boolean checkRule(ArrayList<Rule> rulesList) {

        boolean result = true;

        for (Rule rule : rulesList) {

            result = result && rule.check(this);
        }

        return result;
    }

    public boolean checkRule(Rule rule) {

        return rule.check(this);
    }

    public Lesson[][] getLessonArray() {

        return this.lessonArray;
    }
}
