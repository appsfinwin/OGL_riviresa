package com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchemesResponse {

    @SerializedName("data")
    @Expose
    private List<SchemeData> data = null;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SchemeData> getData() {
        return data;
    }

    public void setData(List<SchemeData> data) {
        this.data = data;
    }

}