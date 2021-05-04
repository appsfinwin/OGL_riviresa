package com.rivaresa.cusmateogl.account_list.pojo.inventory_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InventoryDetailResponse {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("ornaments")
    @Expose
    private List<Ornament> ornaments = null;

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

    public List<Ornament> getOrnaments() {
        return ornaments;
    }

    public void setOrnaments(List<Ornament> ornaments) {
        this.ornaments = ornaments;
    }

}