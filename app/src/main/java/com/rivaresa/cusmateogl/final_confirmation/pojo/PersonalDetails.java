package com.rivaresa.cusmateogl.final_confirmation.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalDetails {

@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("address")
@Expose
private String address;
@SerializedName("post_office")
@Expose
private String postOffice;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("place")
@Expose
private String place;
@SerializedName("mobile_number")
@Expose
private String mobileNumber;

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getPostOffice() {
return postOffice;
}

public void setPostOffice(String postOffice) {
this.postOffice = postOffice;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getPlace() {
return place;
}

public void setPlace(String place) {
this.place = place;
}

public String getMobileNumber() {
return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
this.mobileNumber = mobileNumber;
}

}