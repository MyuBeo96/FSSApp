package com.myubeo.fssapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.myubeo.fssapp.LoginActivity;
import com.myubeo.fssapp.R;
import com.myubeo.fssapp.adapter.ProjectAdapter;
import com.myubeo.fssapp.model.createModel.Project;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {

    ImageButton btn_add_day;
    EditText edt_desc;
    Button btn_logout;
    LinearLayout layout_day;
    Spinner title_project;
    ProjectAdapter projectAdapter;
    List<Project> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_add_day = (ImageButton) view.findViewById(R.id.btn_add_day);
        edt_desc = (EditText) view.findViewById(R.id.edt_desc);
        layout_day = (LinearLayout) view.findViewById(R.id.layout_day);
        title_project = (Spinner) view.findViewById(R.id.title_project);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
//        String name = sharedPreferences.getString("nameProject", "Default");
//        String id = sharedPreferences.getString("idProject", "Default");
//        long time = sharedPreferences.getLong("timeClick", 0);
//        if(time < System.currentTimeMillis()) {
//            Log.d("test", "onViewCreated: " + name + " - " + time);
//        }

        list = new ArrayList<>();
        list.addAll(Project.getRecentList());
        list.addAll(Project.getConstantList());
      /*  list.add(new Project("1", "Java"));
        list.add(new Project("2", "Android"));
        list.add(new Project("3", "PHP"));
        list.add(new Project("4", "C#"));
        list.add(new Project("5", "ASP.NET"));
        list.add(new Project("6", "Laravel"));
        list.add(new Project("7", "React native"));
        list.add(new Project("8", "IOS"));*/


        projectAdapter = new ProjectAdapter(getContext(), R.layout.item_project, list);
        title_project.setAdapter(projectAdapter);

        initListener();

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
//                Toast.makeText(getContext(), title_project.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                long time= System.currentTimeMillis();
//                Log.d("test", "onItemSelected: " + time);
//                SharedPreferences.Editor editor = getContext().getSharedPreferences("project", Context.MODE_PRIVATE).edit();
//                editor.putString("nameProject", list.get(i).getProjectName());
//                editor.putString("idProject", list.get(i).getId());
//                editor.putLong("timeClick", time);
//                editor.apply();
                String projectName = list.get(i).getProjectName();
                String projectId = list.get(i).getId();
                new Project(projectId, projectName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
