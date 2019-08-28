package com.myubeo.fssapp.model.createModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myubeo.fssapp.MainActivity;
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Project implements Comparable<Project>{

    private static List<Project> recentList = new ArrayList<>();
    private static List<Project> constantsObjectList = new ArrayList<>();
    private String id;
    private String projectName;
    private long time;
    private static String name = null;
    private static String projectID = null;

    public Project( String id, String projectName) {
        this.id = id;
        this.projectName = projectName;
        this.time = System.currentTimeMillis();
        addToRecentList(this);
    }

    static
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "1");
        jsonObject.addProperty("jsonrpc", "2.0");
        jsonObject.addProperty("method", "getProjects");

        JsonArray paramsArray = new JsonArray();
        paramsArray.add(MainActivity.getApiKey());
        jsonObject.add("params", paramsArray);

        APIInterface client = APIUtils.getData();

        Call<JsonObject> activityList = client.postRawJSON(jsonObject);

        activityList.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));

                        JSONArray items = object.getJSONObject("result").getJSONArray("items");

                        for(int i = 0; i < items.length(); i++) {
                            JSONObject key = items.getJSONObject(i);
                            name = key.getString("name");
                            projectID = key.getString("projectID");
                            Project project = new Project(projectID, name);
                            constantsObjectList.add(project);
                            project.time = i;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        Collections.sort(constantsObjectList, new ProjectComparator());
    }

    public static String getName()
    {
        return name;
    }

    public static String getProjectID()
    {
        return projectID;
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

    static class ProjectComparator implements Comparator<Project> {

        @Override
        public int compare(Project o1, Project o2) {
            return o2.projectName.compareTo(o1.projectName);
        }
    }
}
