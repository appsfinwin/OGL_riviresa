package com.rivaresa.cusmateogl.payment.paytm.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettlementDetailsResponse {

    @SerializedName("data")
    @Expose
    private List<SettlementData> data = null;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SettlementData> getData() {
        return data;
    }

    public void setData(List<SettlementData> data) {
        this.data = data;
    }

    public SettlementDetailsResponse() {
    }
}