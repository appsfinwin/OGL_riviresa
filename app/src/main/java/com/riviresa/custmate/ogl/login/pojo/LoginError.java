package com.riviresa.custmate.ogl.login.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginError {

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