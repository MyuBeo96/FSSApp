package com.myubeo.fssapp.model;

public class TimeSheetsModel {
    String day;
    String time;
    String duration;
    String projectName;
    String activityName;
    String description;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TimeSheetsModel(String day, String time, String duration, String projectName, String activityName, String description, String status) {
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.projectName = projectName;
        this.activityName = activityName;
        this.description = description;
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
