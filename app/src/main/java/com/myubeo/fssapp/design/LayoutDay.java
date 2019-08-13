package com.myubeo.fssapp.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.myubeo.fssapp.R;
public class LayoutDay extends LinearLayout {

    Context context;
    ImageButton btn_time;
    LinearLayout layout_time;

    public LayoutDay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_day, this);

        btn_time = findViewById(R.id.btn_time);
        layout_time = findViewById(R.id.layout_time);

        btn_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View viewTime = layoutInflater.inflate(R.layout.layout_time, null);
                layout_time.addView(viewTime);

            }
        });
    }
}
