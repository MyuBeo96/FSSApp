package com.myubeo.fssapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myubeo.fssapp.MainActivity;
import com.myubeo.fssapp.R;
import com.myubeo.fssapp.adapter.ActivityModelAdapter;
import com.myubeo.fssapp.adapter.ProjectModelAdapter;
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;
import com.myubeo.fssapp.model.createModel.Activity;
import com.myubeo.fssapp.model.createModel.ActivityModel;
import com.myubeo.fssapp.model.createModel.Project;
import com.myubeo.fssapp.model.createModel.ProjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordFragment extends Fragment {

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    LinearLayout linearLayout;
    Spinner projectName;
    Spinner activityName;
    List<ProjectModel> projectModelList;
    List<ProjectModel> projectModels;
    List<ActivityModel> activityModelList;
    ProjectModelAdapter projectModelAdapter;
    ActivityModelAdapter activityModelAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.layout);
        projectName = view.findViewById(R.id.projectName);
        activityName = view.findViewById(R.id.activityName);

        projectModels = new ArrayList<>();
        projectModelList = new ArrayList<>();
        listProject();

        projectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityModelList = new ArrayList<>();
                listActivity(projectModelList.get(position).getProjectId());
                projectModels.add(new ProjectModel(projectModelList.get(position).getProjectId(), projectModelList.get(position).getProjectName()));
                Log.d("test", "onItemSelected: " + projectModels);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void listProject(){
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
                            String name = key.getString("name");
                            String projectID = key.getString("projectID");
                            projectModelList.add(new ProjectModel(projectID, name));
                        }
                        projectModelAdapter = new ProjectModelAdapter(getContext(), R.layout.item_project, projectModelList);
                        projectName.setAdapter(projectModelAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void listActivity(String projectID){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "1");
        jsonObject.addProperty("jsonrpc", "2.0");
        jsonObject.addProperty("method", "getTasks");

        JsonArray paramsArray = new JsonArray();
        paramsArray.add(MainActivity.getApiKey());
        paramsArray.add(projectID);
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
                            activityModelList.add(new ActivityModel(activityID, name));
                        }

                        activityModelAdapter = new ActivityModelAdapter(getContext(), R.layout.item_project, activityModelList);
                        activityName.setAdapter(activityModelAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
