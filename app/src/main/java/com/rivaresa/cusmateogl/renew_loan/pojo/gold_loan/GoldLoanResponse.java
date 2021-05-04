package com.rivaresa.cusmateogl.renew_loan.pojo.gold_loan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response.Receipt;

public class GoldLoanResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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