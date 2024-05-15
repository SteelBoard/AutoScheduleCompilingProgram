package com.example.timetablecompiler.model;

import com.example.timetablecompiler.model.rules.PositionRule;
import com.example.timetablecompiler.model.rules.Rule;
import com.example.timetablecompiler.util.TransformUtil;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;

public class Schedule {

    private Lesson[][] lessonArray;

    public Schedule() {

        this.lessonArray = new Lesson[5][8];
    }

    public static Schedule generateRandom(ArrayList<Rule> rules) {

        Schedule schedule = new Schedule();
        List<String> shuffledList = TransformUtil.HashMapOfSubjectToArrayList(DbSubjectsDataModel.getQuantityOfSubjects());
        shuffle(shuffledList);
        HashMap<String, String> subjects_teachers = DbSubjectsDataModel.getSubjectWithTeachers();
        HashMap<String, Integer> subjects_classrooms = DbSubjectsDataModel.getClassrooms();

        for (Rule rule : rules) {

            if (rule instanceof PositionRule positionRule) {

                if (schedule.getLessonArray()[positionRule.getDay().getNumber()-1][positionRule.getLessonNumber().getNumber()-1] == null && shuffledList.contains(positionRule.getSubject())) {

                    schedule.getLessonArray()[positionRule.getDay().getNumber()-1][positionRule.getLessonNumber().getNumber()-1] =
                            new Lesson(positionRule.getSubject(), subjects_teachers.get(positionRule.getSubject()), subjects_classrooms.get(positionRule.getSubject()));
                    shuffledList.remove(positionRule.getSubject());
                }
                else if (!shuffledList.contains(positionRule.getSubject()) || !Objects.equals(positionRule.getSubject(), schedule.getLessonArray()[positionRule.getDay().getNumber() - 1][positionRule.getLessonNumber().getNumber() - 1].getSubject())) {

                    return null;
                }
            }
        }


        for (int i = 0; i < schedule.getLessonArray().length; i++) {

            for (int j = 0; j < 6; j++) {

                if (schedule.getLessonArray()[i][j] == null) {

                    for (String subject : shuffledList) {

                        schedule.getLessonArray()[i][j] = new Lesson(subject, subjects_teachers.get(subject), subjects_classrooms.get(subject));
                        if (schedule.checkRule(rules) &&
                                Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 3) {

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

        if (!shuffledList.isEmpty()) {

            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 8; j++) {

                    if (schedule.getLessonArray()[i][j] == null) {

                        for (String subject : shuffledList) {

                            schedule.getLessonArray()[i][j] = new Lesson(subject, subjects_teachers.get(subject), subjects_classrooms.get(subject));
                            if (schedule.checkRule(rules)
                                    && Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 3) {

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

    public static Schedule generate(ArrayList<Rule> rules, Classes grade) {

        HashMap<String, ArrayList<String>> subjectsWithTeachers = DbSubjectsDataModel.getTeachersWithSubjects();
        List<String> subjects = TransformUtil.HashMapOfSubjectToArrayList(DbSubjectsDataModel.getQuantityOfSubjects());
        shuffle(subjects);
        HashMap<String, Integer> classrooms = DbSubjectsDataModel.getClassrooms();

        Schedule schedule = new Schedule();

        for (Rule rule : rules) {

            if (rule instanceof PositionRule currentRule) {

                List<String> freeTeachers = subjectsWithTeachers.get(currentRule.getSubject()).stream().filter(teacher -> isTeacherFree(currentRule.getDay(), currentRule.getLessonNumber(), teacher, grade)).toList();

                if (schedule.getLessonArray()[currentRule.getDay().getNumber() - 1][currentRule.getLessonNumber().getNumber() - 1] == null
                        && !freeTeachers.isEmpty() && subjects.contains(currentRule.getSubject()) && isClassroomFree(currentRule.getDay(), currentRule.getLessonNumber(), classrooms.get(currentRule.getSubject()), grade)) {

                    schedule.getLessonArray()[currentRule.getDay().getNumber() - 1][currentRule.getLessonNumber().getNumber() - 1]
                            = new Lesson(currentRule.getSubject(), freeTeachers.get(0), classrooms.get(currentRule.getSubject()));
                    subjects.remove(currentRule.getSubject());
                } else if (Objects.equals(schedule.getLessonArray()[currentRule.getDay().getNumber() - 1][currentRule.getLessonNumber().getNumber() - 1].getSubject(), currentRule.getSubject())
                        && subjects.contains(currentRule.getSubject())) {

                    continue;
                } else {

                    return null;
                }
            }
        }

        for (int i = 0; i < schedule.getLessonArray().length; i++) {

            for (int j = 0; j < 6; j++) {

                if (schedule.getLessonArray()[i][j] == null) {

                    for (String subject : subjects) {

                        List<String> freeTeachers = new ArrayList<>();
                        ArrayList<String> get = subjectsWithTeachers.get(subject);
                        for (String teacher : get) {

                            if (isTeacherFree(Weekdays.getWeekdayByNumber(i + 1), LessonTimes.getLessonTimeByNumber(j + 1), teacher, grade)) {

                                freeTeachers.add(teacher);
                            }
                        }

                        if (!freeTeachers.isEmpty()) {

                            schedule.getLessonArray()[i][j] = new Lesson(subject, freeTeachers.get(0), classrooms.get(subject));
                        }
                        else {

                            continue;
                        }

                        if (schedule.checkRule(rules) &&
                                Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 3
                                && isClassroomFree(Weekdays.getWeekdayByNumber(i+1), LessonTimes.getLessonTimeByNumber(j+1), classrooms.get(subject), grade)) {

                            subjects.remove(subject);
                            break;
                        }
                        else {

                            schedule.getLessonArray()[i][j] = null;
                        }
                    }
                }
            }
        }

        if (subjects.isEmpty()) {

            return schedule;
        }

        for (int i = 0; i < schedule.getLessonArray().length; i++) {

            for (int j = 0; j < 8; j++) {

                if (schedule.getLessonArray()[i][j] == null) {

                    for (String subject : subjects) {

                        List<String> freeTeachers = new ArrayList<>();
                        for (String teacher: subjectsWithTeachers.get(subject)) {

                            if (isTeacherFree(Weekdays.getWeekdayByNumber(i +1), LessonTimes.getLessonTimeByNumber(j +1), teacher, grade)) {

                                freeTeachers.add(teacher);
                            }
                        }

                        if (!freeTeachers.isEmpty()) {

                            schedule.getLessonArray()[i][j] = new Lesson(subject, freeTeachers.get(0), classrooms.get(subject));
                        }
                        else {

                            continue;
                        }

                        if (schedule.checkRule(rules) &&
                                Arrays.stream(schedule.getLessonArray()[i]).filter(lesson -> lesson != null && lesson.getSubject().equals(subject)).count() < 3
                                && isClassroomFree(Weekdays.getWeekdayByNumber(i+1), LessonTimes.getLessonTimeByNumber(j+1), classrooms.get(subject), grade)) {

                            subjects.remove(subject);
                            break;
                        }
                        else {

                            schedule.getLessonArray()[i][j] = null;
                        }
                    }
                }
            }
        }

        if (subjects.isEmpty()) {

            return schedule;
        }
        else {

            return null;
        }
    }

    public static boolean isTeacherFree(Weekdays day, LessonTimes lesson, String teacher, Classes currentGrade) {

        ArrayList<Schedule> schedules = new ArrayList<>(Arrays.asList(DbScheduleDataModel.getSchedule(Classes.A),
                DbScheduleDataModel.getSchedule(Classes.B), DbScheduleDataModel.getSchedule(Classes.C),
                DbScheduleDataModel.getSchedule(Classes.D)));

        boolean result = true;
        for (Schedule schedule : schedules) {

            if (schedule.equals(DbScheduleDataModel.getSchedule(currentGrade)) || schedule.lessonArray[day.getNumber() - 1][lesson.getNumber() - 1] == null) {

                continue;
            }
            result = result && !Objects.equals(schedule.lessonArray[day.getNumber() - 1][lesson.getNumber() - 1].getTeacher(), teacher);
        }

        return result;
    }

    public static boolean isClassroomFree(Weekdays day, LessonTimes lesson, Integer classroom, Classes currentGrade) {

        ArrayList<Schedule> schedules = new ArrayList<>(Arrays.asList(DbScheduleDataModel.getSchedule(Classes.A),
                DbScheduleDataModel.getSchedule(Classes.B), DbScheduleDataModel.getSchedule(Classes.C),
                DbScheduleDataModel.getSchedule(Classes.D)));

        boolean result = true;
        for (Schedule schedule : schedules) {

            if (schedule.equals(DbScheduleDataModel.getSchedule(currentGrade)) || schedule.lessonArray[day.getNumber() - 1][lesson.getNumber() - 1] == null) {

                continue;
            }
            result = result && !Objects.equals(schedule.lessonArray[day.getNumber() - 1][lesson.getNumber() - 1].getClassroom(), classroom);
        }

        return result;
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

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Schedule otherSchedule)) {

            return false;
        }

        boolean result = true;
        for (int i = 0; i < this.getLessonArray().length; i++) {

            for (int j = 0; j < this.lessonArray[i].length; j++) {

                result = result && Objects.equals(this.lessonArray[i][j], otherSchedule.lessonArray[i][j]);
            }
        }

        return result;
    }
}
