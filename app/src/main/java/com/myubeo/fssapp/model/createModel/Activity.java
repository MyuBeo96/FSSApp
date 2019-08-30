package com.myubeo.fssapp.model.createModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myubeo.fssapp.MainActivity;
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;
import com.myubeo.fssapp.fragment.CreateFragment;

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

public class Activity implements Comparable<Activity> {

    private static List<Activity> recentList = new ArrayList<>();
    private static List<Activity> constantsObjectList = new ArrayList<>();
    private String id;
    private String activityName;
    private long time;

    public Activity( String id, String activityName) {
        this.id = id;
        this.activityName = activityName;
        this.time = System.currentTimeMillis();
        addToRecentList(this);
    }

    static
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "1");
        jsonObject.addProperty("jsonrpc", "2.0");
        jsonObject.addProperty("method", "getTasks");

        JsonArray paramsArray = new JsonArray();
        paramsArray.add(MainActivity.getApiKey());
        paramsArray.add(Project.getProjectID());
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
                            String name = key.getString("name");
                            String activityID = key.getString("activityID");
                            Activity project = new Activity(activityID, name);
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
        Collections.sort(constantsObjectList, new Activity.ActivityComparator());
    }

    private void addToRecentList(Activity activity) {
        if (recentList.contains(activity)) {
            recentList.remove(activity);
            recentList.add(activity);
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public static List<Activity> getRecentList() {
        Collections.sort(recentList);
        return recentList;
    }

    public static List<Activity> getConstantList() {
        Collections.sort(constantsObjectList);
        return constantsObjectList;
    }

    @Override
    public int compareTo(Activity o) {
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
        else if (object instanceof Activity) {
            Activity that = (Activity) object;
            return that.id.equals(this.id);
        } else {
            return false;
        }
    }

    static class ActivityComparator implements Comparator<Activity> {

        @Override
        public int compare(Activity o1, Activity o2) {
            return o2.activityName.compareTo(o1.activityName);
        }
    }
}
