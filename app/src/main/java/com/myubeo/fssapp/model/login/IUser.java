package com.myubeo.fssapp.model.login;

public interface IUser {
    String getName();

    String getPassword();

    int checkUserValidity(String userName, String password);
}
