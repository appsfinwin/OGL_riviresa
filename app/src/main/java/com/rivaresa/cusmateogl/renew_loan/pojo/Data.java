package com.rivaresa.cusmateogl.renew_loan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("Tranid")
@Expose
private String tranid;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("AccountNo")
@Expose
private String accountNo;
@SerializedName("Date")
@Expose
private String date;
@SerializedName("Time")
@Expose
private String time;
@SerializedName("Status")
@Expose
private String status;

public String getTranid() {
return tranid;
}

public void setTranid(String tranid) {
this.tranid = tranid;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getAccountNo() {
return accountNo;
}

public void setAccountNo(String accountNo) {
this.accountNo = accountNo;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}