package com.example.timetablecompiler.model;

import com.example.timetablecompiler.model.rules.Rule;
import com.example.timetablecompiler.util.TransformUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static java.util.Collections.shuffle;

public class Schedule {

    private Lesson[][] lessonArray;

    public Schedule(Lesson[][] lessonList) {

        this.lessonArray = lessonList;
    }

    public Schedule() {

        this.lessonArray = new Lesson[5][8];
    }

    public static Schedule generate(ArrayList<Rule> rules) {

        Schedule schedule = new Schedule(new Lesson[5][8]);
        List<String> shuffledList = TransformUtil.HashMapOfSubjectToArrayList(DbSubjectsDataModel.getQuantityOfSubjects());
        shuffle(shuffledList);
        HashMap<String, String> subjects_teachers = DbSubjectsDataModel.getSubjectWithTeachers();


        for (int i = 0; i < schedule.getLessonArray().length; i++) {

            for (int j = 0; j < 6; j++) {

                for (String subject : shuffledList) {

                    schedule.getLessonArray()[i][j] = new Lesson(subject, subjects_teachers.get(subject));
                    if (schedule.checkRule(rules) &&
                            Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 2) {

                        shuffledList.remove(subject);
                        break;
                    }
                    else {

                        schedule.getLessonArray()[i][j] = null;
                    }
                }
            }
        }

        if (!shuffledList.isEmpty()) {

            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 8; j++) {

                    if (schedule.getLessonArray()[i][j] == null) {

                        for (String subject : shuffledList) {

                            schedule.getLessonArray()[i][j] = new Lesson(subject, subjects_teachers.get(subject));
                            if (schedule.checkRule(rules)
                                    && Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 2) {

                                shuffledList.remove(subject);
                                break;
                            }
                            else {

                                schedule.getLessonArray()[i][j] = null;
                            }

                        }
                    }
                }
            }
        }

        if (shuffledList.isEmpty()) {

            return schedule;
        }
        else {

            return null;
        }
    }

    public static Schedule generateRandom() {

        HashMap<String, Integer> quantity = DbSubjectsDataModel.getQuantityOfSubjects();
        HashMap<String, String> subjectsTeachers = DbSubjectsDataModel.getSubjectWithTeachers();
        Schedule schedule = new Schedule(new Lesson[5][6]);


        int maxLessonsInDay = 2;

        ArrayList<String> subjectShuffledList = TransformUtil.HashMapOfSubjectToArrayList(quantity);
        shuffle(subjectShuffledList);

        for (int i = 0, currentSubject = 0; i < schedule.getLessonArray().length; i++) {

            for (int j = 0; j < schedule.getLessonArray()[i].length; j++) {


            }
        }

        return schedule;
    }

    public boolean checkRule(ArrayList<Rule> rulesList) {

        if (rulesList == null || rulesList.isEmpty()) {

            return true;
        }

        boolean result = true;
        for (Rule rule : rulesList) {

            result = result && rule.check(this);
        }
        return result;
    }

    public boolean checkRule(Rule rule) {

        if (rule == null) {

            return true;
        }
        else {

            return rule.check(this);
        }
    }

    public Lesson[][] getLessonArray() {

        return this.lessonArray;
    }


}
