package com.myubeo.fssapp.design;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

public class LayoutDay extends LinearLayout {

    Context context;
    ImageButton btn_time;
    LinearLayout layout_time;

    SeekBar seekbar_morning;
    TextView time_morning;
    EditText duration_Hour;
    EditText duration_Minutes;
    EditText edt_desc;

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

        btn_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View viewTime = layoutInflater.inflate(R.layout.item_time, null);
                layout_time.addView(viewTime);

                getView(viewTime);
            }
        });
    }

    private void getView(View view){

        seekbar_morning = view.findViewById(R.id.seekbar_morning);
        time_morning = view.findViewById(R.id.time_morning);
        duration_Hour = view.findViewById(R.id.duration_Hour);
        duration_Minutes = view.findViewById(R.id.duration_Minutes);
        edt_desc = view.findViewById(R.id.edt_desc);

        duration_Hour.addTextChangedListener(new PinTextWatcher(duration_Hour));
        duration_Minutes.addTextChangedListener(new PinTextWatcher(duration_Minutes));
        seekbar_morning.setProgress(210);
        seekbar_morning.incrementProgressBy(15);

        String value = startTime + " - " + finishTime;
        time_morning.setText(value);

        try {
            long duration = df.parse(finishTime).getTime() -  df.parse(startTime).getTime();
            long hours = ((duration / 1000) / 60) / 60;
            long minutes = (((duration / 1000) / 60) - hours * 60) % 60;
            duration_Hour.setText(String.valueOf(hours));
            duration_Minutes.setText(String.valueOf(minutes));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public class PinTextWatcher implements TextWatcher {

        private View view;

        private PinTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() != 2)
                return;
            switch (view.getId()) {
                case R.id.duration_Hour:
                    if (text.length() == 2) {
                        duration_Minutes.requestFocus();

                    }
                    break;

                case R.id.duration_Minutes:
                    if (text.length() == 2)
                        edt_desc.requestFocus();
                    break;
            }
        }
    }
}
