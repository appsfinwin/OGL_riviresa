package com.rivaresa.cusmateogl.account_list.pojo.account_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountDetailsResponse {

    @SerializedName("data")
    @Expose
    private List<AccountData> data = null;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<AccountData> getData() {
        return data;
    }

    public void setData(List<AccountData> data) {
        this.data = data;
    }

}