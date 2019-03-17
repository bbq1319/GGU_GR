package com.ggu.gguri.pojo;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInfo {

    private static LoginInfo instance = null;

    @SerializedName("major")
    @Expose
    private String major;
    @SerializedName("major2")
    @Expose
    private String major2;
    @SerializedName("auth")
    @Expose
    private String auth;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nicknm")
    @Expose
    private String nicknm;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("stuno")
    @Expose
    private String stuno;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor2() {
        return major2;
    }

    public void setMajor2(String major2) {
        this.major2 = major2;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicknm() {
        return nicknm;
    }

    public void setNicknm(String nicknm) {
        this.nicknm = nicknm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public static LoginInfo getInstance() {
        if(instance == null) {
            instance = new LoginInfo();
        }
        return instance;
    }

}