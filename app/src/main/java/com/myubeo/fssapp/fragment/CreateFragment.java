package com.myubeo.fssapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.myubeo.fssapp.LoginActivity;
import com.myubeo.fssapp.R;
import com.myubeo.fssapp.adapter.ActivityAdapter;
import com.myubeo.fssapp.adapter.ProjectAdapter;
import com.myubeo.fssapp.model.createModel.Activity;
import com.myubeo.fssapp.model.createModel.Project;
import com.myubeo.fssapp.model.createModel.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {

    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
        return fragment;
    }

    ImageButton btn_add_day;
    EditText edt_desc;
    Button btn_logout;
    LinearLayout layout_day;
    Spinner title_project;
    Spinner title_activity;
    ProjectAdapter projectAdapter;
    ActivityAdapter activityAdapter;
    List<Project> projectList;
    List<Activity> activityList;
    List<ProjectModel> projectModelList;
    private static String projectID = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        projectModelList = new ArrayList<>();

        btn_add_day = (ImageButton) view.findViewById(R.id.btn_add_day);
        edt_desc = (EditText) view.findViewById(R.id.edt_desc);
        layout_day = (LinearLayout) view.findViewById(R.id.layout_day);
        title_project = (Spinner) view.findViewById(R.id.title_project);
        title_activity = (Spinner) view.findViewById(R.id.title_activity);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        projectList = new ArrayList<>();
        projectList.addAll(Project.getRecentList());
        projectList.addAll(Project.getConstantList());


        projectAdapter = new ProjectAdapter(getContext(), R.layout.item_project, projectList);
        title_project.setAdapter(projectAdapter);

        activityList = new ArrayList<>();
        activityList.addAll(Activity.getRecentList());
        activityList.addAll(Activity.getConstantList());


        activityAdapter = new ActivityAdapter(getContext(), R.layout.item_project, activityList);
        title_activity.setAdapter(activityAdapter);

        initListener();
        for (int i = 0; i < projectModelList.size(); i++) {
            Log.d("test", "onResume: " + projectModelList.get(i).getProjectId());
        }

    }

    private void initListener(){
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_add_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(getContext());
                View myView = factory.inflate(R.layout.layout_day, null);
                layout_day.addView(myView);
            }
        });

        title_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                projectAdapter.notifyDataSetChanged();
                String projectName = projectList.get(i).getProjectName();
                projectID = projectList.get(i).getId();
                Log.d("test", "getProjectID: " + projectID);
                projectModelList.add(new ProjectModel(projectID, projectName));
                new Project(projectID, projectName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        title_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                activityAdapter.notifyDataSetChanged();
                String activityName = activityList.get(i).getActivityName();
                String activityID = activityList.get(i).getId();
                new Activity(activityID, activityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
