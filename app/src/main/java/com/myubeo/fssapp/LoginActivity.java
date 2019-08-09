package com.myubeo.fssapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.myubeo.fssapp.design.CircleImage;
import com.myubeo.fssapp.presenter.login.ILoginPresenter;
import com.myubeo.fssapp.presenter.login.LoginPresenter;
import com.myubeo.fssapp.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    EditText edt_login_username;
    EditText edt_login_pass;
    Button btn_login;

    ILoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_login);
        Bitmap circularBitmap = CircleImage.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);

        iLoginPresenter = new LoginPresenter(this);

        initView();
        initListener();
    }

    private void initView(){
        edt_login_username = findViewById(R.id.edt_login_username);
        edt_login_pass = findViewById(R.id.edt_login_pass);
        btn_login = findViewById(R.id.btn_login);
    }

    private void initListener(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                iLoginPresenter.doLogin(edt_login_username.getText().toString(), edt_login_pass.getText().toString());
            }
        });
    }

    @Override
    public void onClearText() {
        edt_login_username.setText("");
        edt_login_pass.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        btn_login.setEnabled(true);
        if (result){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
//            Toast.makeText(this,"Login Fail, code = " + code,Toast.LENGTH_SHORT).show();
        Log.d("test", "onLoginResult: thất bại");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
