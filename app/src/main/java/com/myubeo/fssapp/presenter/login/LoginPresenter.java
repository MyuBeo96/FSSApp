package com.myubeo.fssapp.presenter.login;

import android.os.Handler;
import android.os.Looper;

import com.myubeo.fssapp.model.login.IUser;
import com.myubeo.fssapp.model.login.UserModel;
import com.myubeo.fssapp.view.ILoginView;

public class LoginPresenter implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwd) {
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name,passwd);
        if (code!=0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);
    }

    private void initUser(){
        user = new UserModel("linh.vu","123456");
    }

}
