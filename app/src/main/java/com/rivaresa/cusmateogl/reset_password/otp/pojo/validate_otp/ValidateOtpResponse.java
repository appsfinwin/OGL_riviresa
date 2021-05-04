package com.rivaresa.cusmateogl.reset_password.otp.pojo.validate_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateOtpResponse {

@SerializedName("otp")
@Expose
private Otp otp;

public Otp getOtp() {
return otp;
}

public void setOtp(Otp otp) {
this.otp = otp;
}

}