package com.myubeo.fssapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.myubeo.fssapp.R;

public class CreateFragment extends Fragment {

    ImageButton btn_add_day;
    EditText edt_desc;
    LinearLayout layout_day;


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

        initListener();

    }

    private void initListener(){
        btn_add_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(getContext());
                View myView = factory.inflate(R.layout.item_day, null);
                layout_day.addView(myView);
            }
        });

    }

}
