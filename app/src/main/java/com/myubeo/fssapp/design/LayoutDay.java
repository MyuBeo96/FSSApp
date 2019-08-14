package com.myubeo.fssapp.design;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.myubeo.fssapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class LayoutDay extends LinearLayout {

    Context context;
    ImageButton btn_time;
    LinearLayout layout_time;
    SeekBar seekBar;
    TextView time_morning;
    EditText duration_Hour;
    EditText duration_Minutes;
    public static final String MY_PROGRESS = "MyProgress";
    long timeOnPoint;
    Date dateOnPoint;

    private final String pattern = "HH:mm";
    private final String startTime = "08:30";
    private final String finishTime = "12:00";
    private DateFormat df = new SimpleDateFormat(pattern);
    private DateFormat showHourDf = new SimpleDateFormat("HH");
    private DateFormat showMinuteDf = new SimpleDateFormat("mm");

    public LayoutDay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_day, this);

        btn_time = findViewById(R.id.btn_time);
        layout_time = findViewById(R.id.layout_time);
        seekBar = findViewById(R.id.seekbar_morning);

        btn_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View viewTime = layoutInflater.inflate(R.layout.layout_time, null);
                layout_time.addView(viewTime);
                getView(viewTime);
                seekBar.setEnabled(false);
            }
        });
    }

    private void getView(View view){
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PROGRESS, MODE_PRIVATE);
        int progress = prefs.getInt("progress", 0);

        btn_time.setEnabled(false);

        seekBar = view.findViewById(R.id.seekbar_morning);
        time_morning = view.findViewById(R.id.time_morning);
        duration_Hour = view.findViewById(R.id.duration_Hour);
        duration_Minutes = view.findViewById(R.id.duration_Minutes);

        int max = 210 - progress;
        seekBar.setEnabled(false);

        seekBar.setMax(max);
        if(progress == max){
            btn_time.setEnabled(false);
        }

        long progressInMilliSec = progress * 60 * 1000; //progress = 15 minutes

        try {
            timeOnPoint = df.parse(startTime).getTime() + progressInMilliSec;
            dateOnPoint = new Date(timeOnPoint);

            time_morning.setText(df.format(dateOnPoint) + " - " + finishTime);
            long duration = df.parse(finishTime).getTime() -  df.parse(df.format(dateOnPoint)).getTime();
            long hours = ((duration / 1000) / 60) / 60;
            long minutes = (((duration / 1000) / 60) - hours * 60) % 60;
            duration_Hour.setText(String.valueOf(hours));
            duration_Minutes.setText(String.valueOf(minutes));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 15;
                progress = progress * 15;

//                long progressInMilliSec1 = progress * 60 * 1000; //progress = 15 minutes
//
//                try {
//                    long timeOnPoint1 = df.parse(df.format(dateOnPoint)).getTime() + progressInMilliSec1;
//                    Date dateOnPoint1 = new Date(timeOnPoint1);
//                    Date midnight = df.parse("00:00");
//
//                    time_morning.setText(df.format(dateOnPoint) + " - " + df.format(dateOnPoint1));
//                    duration_Hour.setText(showHourDf.format(new Date (midnight.getTime() + progressInMilliSec1)));
//                    duration_Minutes.setText(showMinuteDf.format(new Date (midnight.getTime() + progressInMilliSec1)));
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                duration(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void duration(int progress){
        long progressInMilliSec1 = progress * 60 * 1000; //progress = 15 minutes

        try {
            long timeOnPoint1 = df.parse(df.format(dateOnPoint)).getTime() + progressInMilliSec1;
            Date dateOnPoint1 = new Date(timeOnPoint1);
            Date midnight = df.parse("00:00");

            time_morning.setText(df.format(dateOnPoint) + " - " + df.format(dateOnPoint1));
            duration_Hour.setText(showHourDf.format(new Date (midnight.getTime() + progressInMilliSec1)));
            duration_Minutes.setText(showMinuteDf.format(new Date (midnight.getTime() + progressInMilliSec1)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
