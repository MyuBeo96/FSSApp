package com.myubeo.fssapp.design;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.myubeo.fssapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class LayoutTime extends LinearLayout {

    Context context;
    SeekBar seekbar_morning;
    TextView time_morning;
    EditText duration_Hour;
    EditText duration_Minutes;
    EditText edt_desc;
    int progressSave;
    public static final String MY_PROGRESS = "MyProgress";
    public static final int MAX_PROGRESS = 210;
    public static final int PROGRESS = 15;

    private final String pattern = "HH:mm";
    private final String startTime = "08:30";
    private DateFormat df = new SimpleDateFormat(pattern);
    private DateFormat showHourDf = new SimpleDateFormat("HH");
    private DateFormat showMinuteDf = new SimpleDateFormat("mm");

    public LayoutTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_time, this);

        seekbar_morning = findViewById(R.id.seekbar_morning);
        time_morning = findViewById(R.id.time_morning);
        duration_Hour = findViewById(R.id.duration_Hour);
        duration_Minutes = findViewById(R.id.duration_Minutes);
        edt_desc = findViewById(R.id.edt_desc);

        seekbar_morning.setMax(MAX_PROGRESS);
        seekbar_morning.setProgress(MAX_PROGRESS);
        seekbar_morning.incrementProgressBy(PROGRESS);

        duration(MAX_PROGRESS);

//        long timeOnPoint1 = 0;
//        try {
//            timeOnPoint1 = df.parse(startTime).getTime() + 210 * 1000 * 60;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Date dateOnPoint = new Date(timeOnPoint1);
//
//        String value = startTime + " - " + df.format(dateOnPoint);
//        time_morning.setText(value);
//
//        try {
//            long duration = df.parse(df.format(dateOnPoint)).getTime() -  df.parse(startTime).getTime();
//            long hours = ((duration / 1000) / 60) / 60;
//            long minutes = (((duration / 1000) / 60) - hours * 60) % 60;
//            duration_Hour.setText(String.valueOf(hours));
//            duration_Minutes.setText(String.valueOf(minutes));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        initListener();
    }

    private void initListener(){
        seekbar_morning.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 15;
                progress = progress * 15;

                progressSave = progress;
                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PROGRESS, MODE_PRIVATE).edit();
                editor.putInt("progress", progressSave);
                editor.apply();

//                long progressInMilliSec = progress * 60 * 1000; //progress = 15 minutes
//
//                try {
//                    long timeOnPoint = df.parse(startTime).getTime() + progressInMilliSec;
//                    Date dateOnPoint = new Date(timeOnPoint);
//                    Date midnight = df.parse("00:00");
//
//                    time_morning.setText(startTime + " - " + df.format(dateOnPoint));
//                    duration_Hour.setText(showHourDf.format(new Date (midnight.getTime() + progressInMilliSec)));
//                    duration_Minutes.setText(showMinuteDf.format(new Date (midnight.getTime() + progressInMilliSec)));
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
        long progressInMilliSec = progress * 60 * 1000; //progress = 15 minutes

        try {
            long timeOnPoint = df.parse(startTime).getTime() + progressInMilliSec;
            Date dateOnPoint = new Date(timeOnPoint);
            Date midnight = df.parse("00:00");

            time_morning.setText(startTime + " - " + df.format(dateOnPoint));
            duration_Hour.setText(showHourDf.format(new Date (midnight.getTime() + progressInMilliSec)));
            duration_Minutes.setText(showMinuteDf.format(new Date (midnight.getTime() + progressInMilliSec)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
