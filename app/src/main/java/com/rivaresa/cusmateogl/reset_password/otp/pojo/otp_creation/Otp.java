package com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("otp_id")
    @Expose
    private String otpId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Msg")
    @Expose
    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}