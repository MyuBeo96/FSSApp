package com.myubeo.fssapp.model.login;

public class UserModel implements IUser{
    String name;
    String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String passwd) {
        if (name==null||passwd==null||!name.equals(getName())||!passwd.equals(getPassword())){
            return -1;
        }
        return 0;
    }

    public void setPasswd(String password) {
        this.password = password;
    }
}
