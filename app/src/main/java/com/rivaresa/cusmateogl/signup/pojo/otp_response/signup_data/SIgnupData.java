package com.rivaresa.cusmateogl.signup.pojo.otp_response.signup_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIgnupData {

@SerializedName("account_no")
@Expose
private String accountNo;
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("password")
@Expose
private String password;
@SerializedName("OTP_ID")
@Expose
private String oTPID;

@SerializedName("Name")
@Expose
private String name;

public String getAccountNo() {
return accountNo;
}

public void setAccountNo(String accountNo) {
this.accountNo = accountNo;
}

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getOTPID() {
return oTPID;
}

public void setOTPID(String oTPID) {
this.oTPID = oTPID;
}


public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

    public SIgnupData(String name,String accountNo, String mobileNo, String password, String oTPID) {
        this.accountNo = accountNo;
        this.mobileNo = mobileNo;
        this.password = password;
        this.oTPID = oTPID;

        this.name = name;
    }

    public SIgnupData() {
    }
}