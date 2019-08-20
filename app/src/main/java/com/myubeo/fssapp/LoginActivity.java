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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myubeo.fssapp.connect.APIClient;
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;
import com.myubeo.fssapp.design.CircleImage;
import com.myubeo.fssapp.model.login.User;
import com.myubeo.fssapp.model.login.UserModel;
import com.myubeo.fssapp.presenter.login.ILoginPresenter;
import com.myubeo.fssapp.presenter.login.LoginPresenter;
import com.myubeo.fssapp.view.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edt_login_username;
    EditText edt_login_pass;
    Button btn_login;

    String userName;
    String passWord;

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

//        iLoginPresenter = new LoginPresenter(this);

        initView();
        initListener();
    }

    private void initView() {
        edt_login_username = findViewById(R.id.edt_login_username);
        edt_login_pass = findViewById(R.id.edt_login_pass);
        btn_login = findViewById(R.id.btn_login);
    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = edt_login_username.getText().toString();
                passWord = edt_login_pass.getText().toString();

                if (userName.length() > 0 && passWord.length() > 0) {
//                    final UserModel userModel = new UserModel(userName, passWord);
//                    final List<UserModel> userModelList = new ArrayList<>();
//                    userModelList.add(userModel);

                    List<String> userModelList = new ArrayList<>();
                    userModelList.add(userName);
                    userModelList.add(passWord);

                    List<String> userList = new ArrayList<>(userModelList);

                    User user = new User("2.0", "authenticate", userList, 1);

                    APIInterface client = APIUtils.getData();
                    Call<User> userCall = client.Login(user);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject object=new JSONObject(new Gson().toJson(response.body()));
                                    Log.e("TAG", "onResponse: "+object );
                                    Log.d("test", "response 33: " + new Gson().toJson(response.body()));
                                    Integer id = response.body().getId();
                                    Log.d("test", "response 33: " + id);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "Tài khoản không tồn tại", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
