package org.hse.baseproject;

import com.google.gson.annotations.SerializedName;

public class Lesson {

    @SerializedName("auditorium")
    private String auditorium;

    @SerializedName("building")
    private String building;

    @SerializedName("date")
    private String date;

    @SerializedName("discipline")
    private String discipline;

    @SerializedName("kindOfWork")
    private String kindOfWork;

    @SerializedName("lecturer_title")
    private String lecturer;

    @SerializedName("beginLesson")
    private String beginLesson;

    @SerializedName("endLesson")
    private String endLesson;

    public String getAuditorium() {
        return auditorium;
    }

    public String getBuilding() {
        return building;
    }

    public String getDate() {
        return date;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getKindOfWork() {
        return kindOfWork;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getBeginLesson() {
        return beginLesson;
    }

    public String getEndLesson() {
        return endLesson;
    }
}
