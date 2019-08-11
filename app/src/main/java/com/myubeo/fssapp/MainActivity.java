package com.myubeo.fssapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.myubeo.fssapp.fragment.CreateFragment;
import com.myubeo.fssapp.fragment.RecordFragment;
import com.myubeo.fssapp.fragment.TimeSheetsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        initListener();
    }

    private void initView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()){
                    case R.id.create_time:
                        fragment = new CreateFragment();
                        break;

                    case R.id.record_time:
                        fragment = new RecordFragment();
                        break;

                    case R.id.timesheet:
                        fragment = new TimeSheetsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                return true;
            }
        });
    }
}
