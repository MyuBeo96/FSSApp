package com.myubeo.fssapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myubeo.fssapp.MainActivity;
import com.myubeo.fssapp.R;
import com.myubeo.fssapp.adapter.TimeSheetAdapter;
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;
import com.myubeo.fssapp.model.TimeSheetsModel;
import com.myubeo.fssapp.model.createModel.Activity;
import com.myubeo.fssapp.model.createModel.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSheetsFragment extends Fragment {

    RecyclerView rcv_listTime;
    List<TimeSheetsModel> timeSheetsModels;
    TimeSheetAdapter timeSheetAdapter;
    public String formattedDuration = null;

    private final String pattern = "HH:mm";
    private DateFormat df = new SimpleDateFormat(pattern);

    int number = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timesheets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rcv_listTime = (RecyclerView) view.findViewById(R.id.rcv_listTime);

        listTimeSheets(number);
    }

    public void listTimeSheets(int number){
        timeSheetsModels = new ArrayList<TimeSheetsModel>();
        rcv_listTime.setLayoutManager(new LinearLayoutManager(getContext()));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "1");
        jsonObject.addProperty("jsonrpc", "2.0");
        jsonObject.addProperty("method", "getTimesheet");
        JsonArray paramsArray = new JsonArray();
        paramsArray.add(MainActivity.getApiKey());
        paramsArray.add("0");
        paramsArray.add("0");
        paramsArray.add("-1");
        paramsArray.add(number);
        paramsArray.add("30");
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
                            String start = key.getString("start");
                            String end = key.getString("end");

                            long timeStart = Long.parseLong(start);
                            long timeEnd = Long.parseLong(end);
                            long work = timeEnd - timeStart;

                            Date dateOnPoint = new Date(timeStart);

                            String activityName = key.getString("activityName");
                            String projectName = key.getString("projectName");
                            String description = key.getString("description");
                            String status = key.getString("status");
                            String ModifiedTime = key.getString("ModifiedTime");
                            formattedDuration = key.getString("formattedDuration");
                            timeSheetsModels.add(new TimeSheetsModel(ModifiedTime, df.format(dateOnPoint) , formattedDuration,
                                    projectName,"[ " + activityName + " ]", description, status));
                        }
                        timeSheetAdapter = new TimeSheetAdapter(getContext(), R.layout.item_timesheets, timeSheetsModels);
                        rcv_listTime.setAdapter(timeSheetAdapter);

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
