package com.rivaresa.cusmateogl.reset_password.otp.pojo.validate_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp {

@SerializedName("Msg")
@Expose
private String msg;
@SerializedName("status")
@Expose
private String status;

    @SerializedName("data")
    @Expose
    private Integer data;
    @SerializedName("otp_id")
    @Expose
    private String otpId;


    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }


public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}