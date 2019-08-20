package com.myubeo.fssapp.model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("params")
    @Expose
    private List<String> params = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(String jsonrpc, String method, List<String> params, Integer id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
        this.id = id;
    }
}
