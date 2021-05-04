package com.rivaresa.cusmateogl.contact.pojo.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

}