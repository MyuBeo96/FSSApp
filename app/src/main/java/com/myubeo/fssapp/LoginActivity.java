package com.myubeo.fssapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.myubeo.fssapp.connect.APIInterface;
import com.myubeo.fssapp.connect.APIUtils;
import com.myubeo.fssapp.design.CircleImage;
import com.myubeo.fssapp.fragment.CreateFragment;
import com.myubeo.fssapp.presenter.task.IApiKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    EditText edt_login_username;
    EditText edt_login_pass;
    Button btn_login;

    String userName;
    String passWord;
    String value;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_login);
        Bitmap circularBitmap = CircleImage.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);

        initView();
        initListener();
    }

    private void initView() {
        edt_login_username = findViewById(R.id.edt_login_username);
        edt_login_pass = findViewById(R.id.edt_login_pass);
        btn_login = findViewById(R.id.btn_login);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String user = sharedPreferences.getString("userName", "");
        String pass = sharedPreferences.getString("pass", "");
        edt_login_username.setText(user);
        edt_login_pass.setText(pass);
    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                userName = edt_login_username.getText().toString().trim();
                passWord = edt_login_pass.getText().toString().trim();
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putString("userName", userName);
                editor.putString("pass", passWord);
                editor.apply();

                if (userName.length() > 0 && passWord.length() > 0) {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", "1");
                    jsonObject.addProperty("jsonrpc", "2.0");
                    jsonObject.addProperty("method", "authenticate");

                    JsonArray paramsArray = new JsonArray();
                    paramsArray.add(userName);
                    paramsArray.add(passWord);
                    jsonObject.add("params", paramsArray);

                    APIInterface client = APIUtils.getData();

                    Call<JsonObject> userCall = client.postRawJSON(jsonObject);

                    userCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()) {
                                try {

                                    JSONObject object=new JSONObject(new Gson().toJson(response.body()));

                                    JSONArray items = object.getJSONObject("result").getJSONArray("items");

                                    for(int i = 0; i < items.length(); i++) {
                                        JSONObject key = items.getJSONObject(i);
                                        value = key.getString("apiKey");
                                    }
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("value", value);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "Tài khoản không tồn tại", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên đăng nhập hoặc mật khẩu. Vui lòng kiểm tra lại!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
