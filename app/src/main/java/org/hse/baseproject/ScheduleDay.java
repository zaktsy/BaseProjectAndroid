package org.hse.baseproject;

import java.util.List;

public class ScheduleDay {

    private String day;

    private List<Lesson> lessons;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
