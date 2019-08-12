package com.myubeo.fssapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.myubeo.fssapp.R;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateFragment extends Fragment {

    SeekBar seekbar_morning;
    TextView time_morning;
    EditText duration_Hour;
    EditText duration_Minutes;
    int hourStarts = 8;
    int minuteStarts = 30;
    int hourMorning = 12;
    int minuteMorning = 0;
    int duration = 3;

    int timeWork;
    int hours;
    int minutes;

    private final String pattern = "HH:mm";
    private final String startTime = "08:30";
    private final String finishTime = "12:00";
    private DateFormat df = new SimpleDateFormat(pattern);
    private DateFormat showHourDf = new SimpleDateFormat("HH");
    private DateFormat showMinuteDf = new SimpleDateFormat("mm");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        seekbar_morning = view.findViewById(R.id.seekbar_morning);
        time_morning = view.findViewById(R.id.time_morning);
        duration_Hour = view.findViewById(R.id.duration_Hour);
        duration_Minutes = view.findViewById(R.id.duration_Minutes);

        seekbar_morning.setProgress(210);
        seekbar_morning.incrementProgressBy(15);

//        time_morning.setText(String.valueOf(hourStarts)+ "h" + String.valueOf(minuteStarts) + " - " + String.valueOf(hourMorning) +
//                "h" + ltrim(String.valueOf(minuteMorning), "0", 2));

        String value = startTime + " - " + finishTime;
        time_morning.setText(value);
//
//        duration_Minutes.setText(String.valueOf(minuteStarts));
//        duration_Hour.setText(String.valueOf(duration));
        initListener();

    }

    private void initListener(){
        seekbar_morning.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 15;
                progress = progress * 15;

                long progressInMilliSec = progress * 60 * 1000; //progress = 15 minutes

                try {
                    long timeOnPoint = df.parse(startTime).getTime() + progressInMilliSec;
                    Date dateOnPoint = new Date(timeOnPoint);
                    Date midnight = df.parse("00:00");

                    time_morning.setText(startTime + " - " + df.format(dateOnPoint));
                    duration_Hour.setText(showHourDf.format(new Date (midnight.getTime() + progressInMilliSec)));
                    duration_Minutes.setText(showMinuteDf.format(new Date (midnight.getTime() + progressInMilliSec)));
                    DateFormat dffff = new SimpleDateFormat("HH:mm:ss");
//                    Log.d("test", "onProgressChangedsdsdsd: " + dffff.format(caculatedTime));

                } catch (ParseException e) {
//                    e.printStackTrace();
                    //ko the loi
                }
//                    timeWork = 510 + progress;
//                    hours = timeWork / 60;
//                    minutes = (timeWork - hours * 60) % 60;
//                    time_morning.setText(String.valueOf(hourStarts)+ "h" + String.valueOf(minuteStarts) + " - " + String.valueOf(hours) +
//                        "h" + ltrim(String.valueOf(minutes), "0", 2));
//                    duration_Minutes.setText(String.valueOf(progress % 60));
//                    duration_Hour.setText(String.valueOf(progress / 60));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String ltrim(String input, String filledCharacter, int targetLength) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (int i = 1 ; i <= targetLength - input.length() ; i++) {
            sb.append(filledCharacter);
        }
        sb.append(input);
        return sb.toString();
    }

}
