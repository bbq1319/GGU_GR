package com.ggu.gguri.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("RESULT_CODE")
    @Expose
    private Integer RESULTCODE;
    @SerializedName("loginInfo")
    @Expose
    private LoginInfo loginInfo;

    public Integer getRESULTCODE() {
        return RESULTCODE;
    }

    public void setRESULTCODE(Integer rESULTCODE) {
        this.RESULTCODE = rESULTCODE;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

}