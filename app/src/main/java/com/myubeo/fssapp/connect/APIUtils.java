package com.myubeo.fssapp.connect;

public class APIUtils {
    public static final String Base_Url = "https://ts.fss.com.vn/";

    public static APIInterface getData(){
        return APIClient.getClient(Base_Url).create(APIInterface.class);
    }
}
