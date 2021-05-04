package com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.validate_otp.Otp;

public class OtpCreationResponse {

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