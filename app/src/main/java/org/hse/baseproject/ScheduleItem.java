package org.hse.baseproject;

import java.util.Locale;

public class ScheduleItem {

    private String lessonStart;
    private String lessonEnd;
    private String lessonType;
    private String lessonName;
    private String lessonPlace;
    private String teacher;

    public String getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(String lessonStart) {
        this.lessonStart = lessonStart;
    }

    public String getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(String lessonEnd) {
        this.lessonEnd = lessonEnd;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        lessonType = lessonType.toUpperCase(Locale.ROOT);
        this.lessonType = lessonType;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
