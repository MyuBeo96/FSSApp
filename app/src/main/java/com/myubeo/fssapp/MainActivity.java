package com.myubeo.fssapp;

import android.content.Intent;
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
    private static String value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initView();

        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        CreateFragment createFragment = new CreateFragment();
        createFragment.setArguments(bundle);

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
                        fragment = CreateFragment.newInstance();
                        break;

                    case R.id.record_time:
                        fragment = RecordFragment.newInstance();
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

    public static String getApiKey()
    {
        return value;
    }
}
