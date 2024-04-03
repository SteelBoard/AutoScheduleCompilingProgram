package com.example.timetablecompiler.model;

import com.example.timetablecompiler.model.rules.Rule;

import java.util.*;

import static java.util.Collections.shuffle;

public class ScheduleCompiler {

    //
    public static ArrayList<ArrayList<Lesson>> compileInitialSchedule() {

        ArrayList<ArrayList<Lesson>> schedule = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            schedule.add(new ArrayList<Lesson>());
        }
        HashMap<String, Integer> quantity = DbSubjectsData.getQuantityOfSubjects();
        HashMap<String, String> subjects_teachers = DbSubjectsData.getSubjectWithTeachers();

        int i = 0;
        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {

            while (entry.getValue() != 0) {

                schedule.get(i).add(new Lesson(entry.getKey(), subjects_teachers.get(entry.getKey())));
                entry.setValue(entry.getValue()-1);

                if (i == schedule.size() - 1) {

                    i = 0;
                }
                else {

                    i++;
                }
            }
        }

        return schedule;
    }

    // недописано
    public static Lesson[][] compileRandomSchedule() {

        HashMap<String, Integer> quantity = DbSubjectsData.getQuantityOfSubjects();
        HashMap<String, String> subjectsTeachers = DbSubjectsData.getSubjectWithTeachers();
        Lesson[][] schedule = new Lesson[5][6];


        int maxLessonsInDay = 2;

        ArrayList<String> subjectShuffledList = getSubjectsInList(quantity);
        shuffle(subjectShuffledList);

        for (int i = 0, currentSubject = 0; i < schedule.length; i++) {

            for (int j = 0; j < schedule[i].length; j++) {


            }
        }

        return schedule;
    }

    public static ArrayList<String> getSubjectsInList(HashMap<String, Integer> subjects) {

        ArrayList<String> subjectsList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {

            for (int i = 0; i < entry.getValue(); i++) {

                subjectsList.add(entry.getKey());
            }
        }

        return subjectsList;
    }

    public static Schedule compileFullSchedule(ArrayList<Rule> rules) {

        Schedule schedule = new Schedule(new Lesson[5][8]);
        List<String> shuffledList = getSubjectsInList(DbSubjectsData.getQuantityOfSubjects());
        shuffle(shuffledList);
        HashMap<String, String> subjects_teachers = DbSubjectsData.getSubjectWithTeachers();


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

    public static Schedule compileFinalSchedule(ArrayList<Rule> rules) {

        Schedule schedule = new Schedule(new Lesson[5][8]);
        List<String> shuffledList = getSubjectsInList(DbSubjectsData.getQuantityOfSubjects());
        shuffle(shuffledList);
        HashMap<String, String> subjects_teachers = DbSubjectsData.getSubjectWithTeachers();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 8; j++) {


            }
        }

        return schedule;
    }
}
