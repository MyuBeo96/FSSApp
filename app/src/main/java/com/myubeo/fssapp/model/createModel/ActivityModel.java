package com.myubeo.fssapp.model.createModel;

public class ActivityModel {

    private String id;
    private String activityName;

    public ActivityModel(String id, String activityName) {
        this.id = id;
        this.activityName = activityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
