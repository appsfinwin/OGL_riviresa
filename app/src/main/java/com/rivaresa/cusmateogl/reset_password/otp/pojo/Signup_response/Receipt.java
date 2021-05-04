package com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receipt {

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