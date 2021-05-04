package com.rivaresa.cusmateogl.reset_password.forgot_password.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table1 {

@SerializedName("ReturnMessage")
@Expose
private String returnMessage;
@SerializedName("ReturnID")
@Expose
private String returnID;
@SerializedName("ReturnStatus")
@Expose
private String returnStatus;

public String getReturnMessage() {
return returnMessage;
}

public void setReturnMessage(String returnMessage) {
this.returnMessage = returnMessage;
}

public String getReturnID() {
return returnID;
}

public void setReturnID(String returnID) {
this.returnID = returnID;
}

public String getReturnStatus() {
return returnStatus;
}

public void setReturnStatus(String returnStatus) {
this.returnStatus = returnStatus;
}

}