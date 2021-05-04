package com.rivaresa.cusmateogl.login.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

@SerializedName("Name")
@Expose
private String name;
@SerializedName("Cust_id")
@Expose
private String custId;
@SerializedName("Phone_num")
@Expose
private String phoneNum;
@SerializedName("Email_id")
@Expose
private String emailId;
@SerializedName("d_year")
@Expose
private String dYear;
@SerializedName("d_month")
@Expose
private String dMonth;
@SerializedName("d_day")
@Expose
private String dDay;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCustId() {
return custId;
}

public void setCustId(String custId) {
this.custId = custId;
}

public String getPhoneNum() {
return phoneNum;
}

public void setPhoneNum(String phoneNum) {
this.phoneNum = phoneNum;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getDYear() {
return dYear;
}

public void setDYear(String dYear) {
this.dYear = dYear;
}

public String getDMonth() {
return dMonth;
}

public void setDMonth(String dMonth) {
this.dMonth = dMonth;
}

public String getDDay() {
return dDay;
}

public void setDDay(String dDay) {
this.dDay = dDay;
}

    public LoginData() {
    }
}