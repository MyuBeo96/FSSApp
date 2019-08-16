package com.myubeo.fssapp.model.createModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project implements Comparable<Project>{

    private static List<Project> recentList = new ArrayList<>();
    private static List<Project> constantsObjectList = new ArrayList<>();

    static
    {
        Project _1 = new Project("1", "Java");
        Project _2 =  new Project("2", "Android");
        Project _3 = new Project("3", "PHP");
        Project _4 = new Project("4", "C#");
        Project _5 = new Project("5", "ASP.NET");
        Project _6 = new Project("6", "Laravel");
        Project _7 = new Project("7", "React native");
        Project _8 = new Project("8", "IOS");
        constantsObjectList.add(_1);
        constantsObjectList.add(_2);
        constantsObjectList.add(_3);
        constantsObjectList.add(_4);
        constantsObjectList.add(_5);
        constantsObjectList.add(_6);
        constantsObjectList.add(_7);
        constantsObjectList.add(_8);
        _1.time = 1;
        _2.time = 2;
        _3.time = 3;
        _4.time = 4;
        _5.time = 5;
        _6.time = 6;
        _7.time = 7;
        _8.time = 8;
    }

    private String id;
    private String projectName;
    private long time;

    public Project(String id, String projectName) {
        this.id = id;
        this.projectName = projectName;
        this.time = System.currentTimeMillis();
        addToRecentList(this);
    }

    private void addToRecentList(Project project) {
        if (recentList.contains(project)) {
            recentList.remove(project);
            recentList.add(project);
            return;
        }
        if (!(recentList.size() < 4)) {
            recentList.remove(3);
        }
        recentList.add(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public static List<Project> getRecentList() {
        Collections.sort(recentList);
        return recentList;
    }

    public static List<Project> getConstantList() {
        Collections.sort(constantsObjectList);
        return constantsObjectList;
    }

    @Override
    public int compareTo(Project o) {
        if (this.time - o.time > 0)
            return -1;
        else
            return 1;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        else if (object instanceof Project) {
            Project that = (Project) object;
            return that.id.equals(this.id);
        } else {
            return false;
        }

    }
}
