package com.rivaresa.cusmateogl.model.account_holder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("ACNO")
@Expose
private String acno;
@SerializedName("NAME")
@Expose
private String name;
@SerializedName("CUST_ID")
@Expose
private String custId;
@SerializedName("MOBILE")
@Expose
private String mobile;
@SerializedName("CURRENT_BALANCE")
@Expose
private String currentBalance;
@SerializedName("ACC_STATUS")
@Expose
private String accStatus;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }
}
