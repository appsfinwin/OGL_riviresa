package com.riviresa.custmate.ogl.reset_password.otp.pojo.otp_creation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.riviresa.custmate.ogl.reset_password.otp.pojo.validate_otp.Otp;
import com.riviresa.custmate.ogl.reset_password.otp.pojo.validate_otp.Otp;

public class OtpCreationResponse {

@SerializedName("otp")
@Expose
private com.riviresa.custmate.ogl.reset_password.otp.pojo.validate_otp.Otp otp;

public com.riviresa.custmate.ogl.reset_password.otp.pojo.validate_otp.Otp getOtp() {
return otp;
}

public void setOtp(Otp otp) {
this.otp = otp;
}

}