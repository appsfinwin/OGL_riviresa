package com.rivaresa.cusmateogl.reset_password.forgot_password.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {

@SerializedName("data")
@Expose
private Data data;

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}