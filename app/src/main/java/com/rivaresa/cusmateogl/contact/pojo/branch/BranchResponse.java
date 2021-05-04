package com.rivaresa.cusmateogl.contact.pojo.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchResponse {

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

}