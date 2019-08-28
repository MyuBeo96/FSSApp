package com.myubeo.fssapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myubeo.fssapp.R;
import com.myubeo.fssapp.model.TimeSheetsModel;

import java.util.ArrayList;
import java.util.List;

public class TimeSheetAdapter extends  RecyclerView.Adapter<TimeSheetAdapter.RecyclerViewHolder>{
    List<TimeSheetsModel> timeSheetsModels = new ArrayList<TimeSheetsModel>();
    Context context;
    int myLayout;

    public TimeSheetAdapter(Context context, int myLayout, List<TimeSheetsModel> timeSheetsModels) {
        this.timeSheetsModels = timeSheetsModels;
        this.context = context;
        this.myLayout = myLayout;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_timesheets, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.text_day.setText(timeSheetsModels.get(i).getDay());
            recyclerViewHolder.text_time.setText(timeSheetsModels.get(i).getTime());
            recyclerViewHolder.text_hours.setText(timeSheetsModels.get(i).getDuration());
            recyclerViewHolder.txt_projectName.setText(timeSheetsModels.get(i).getProjectName());
            recyclerViewHolder.txt_des.setText(timeSheetsModels.get(i).getDescription());
            recyclerViewHolder.txt_status.setText(timeSheetsModels.get(i).getStatus());
            recyclerViewHolder.txt_activityName.setText(timeSheetsModels.get(i).getActivityName());
    }

    @Override
    public int getItemCount() {
        return timeSheetsModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text_day;
        TextView text_time;
        TextView text_hours;
        TextView txt_projectName;
        TextView txt_des;
        TextView txt_status;
        TextView txt_activityName;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            text_day = (TextView) itemView.findViewById(R.id.text_day);
            text_time = (TextView) itemView.findViewById(R.id.text_time);
            text_hours = (TextView) itemView.findViewById(R.id.text_hours);
            txt_projectName = (TextView) itemView.findViewById(R.id.txt_projectName);
            txt_des = (TextView) itemView.findViewById(R.id.txt_des);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_activityName = (TextView) itemView.findViewById(R.id.txt_activityName);
        }
    }
}
