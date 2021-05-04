package com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

@SerializedName("user")
@Expose
private User user;

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}
    @SerializedName("receipt")
    @Expose
    private Receipt receipt;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}