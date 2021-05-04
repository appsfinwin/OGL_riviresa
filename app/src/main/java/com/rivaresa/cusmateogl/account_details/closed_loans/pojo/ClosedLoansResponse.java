package com.rivaresa.cusmateogl.account_details.closed_loans.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClosedLoansResponse {

    @SerializedName("data")
    @Expose
    private List<LoansData> data = null;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<LoansData> getData() {
        return data;
    }

    public void setData(List<LoansData> data) {
        this.data = data;
    }

}