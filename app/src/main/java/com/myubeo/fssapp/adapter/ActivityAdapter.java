package com.myubeo.fssapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myubeo.fssapp.R;
import com.myubeo.fssapp.model.createModel.Activity;
import com.myubeo.fssapp.model.createModel.Project;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends BaseAdapter {
    Context context;
    List<Activity> activityList = new ArrayList<>();
    int myLayout;

    public ActivityAdapter(Context context, int myLayout, List<Activity> activityList){
        this.activityList = activityList;
        this.context = context;
        this.myLayout = myLayout;
    }

    @Override
    public int getCount() {
        return  activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_project,parent, false);

        TextView nameProject = (TextView) view.findViewById(R.id.nameProject);
        nameProject.setText(activityList.get(position).getActivityName());

        return view;
    }
}
