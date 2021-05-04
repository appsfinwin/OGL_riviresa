package com.rivaresa.cusmateogl.signup.pojo.otp_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OtpGenerateResponse {

@SerializedName("otp")
@Expose
private OtpData otpData;

public OtpData getOtpData() {
return otpData;
}

public void setOtpData(OtpData otpData) {
this.otpData = otpData;
}

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}