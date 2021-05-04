package com.rivaresa.cusmateogl.account_list.dialog_inventory_details;

import androidx.databinding.BaseObservable;

import com.rivaresa.cusmateogl.account_list.pojo.inventory_response.Ornament;

public class DialogRowViewmodel extends BaseObservable {

    Ornament ornament;
    String type,nos,grossWeight,less,netWeight;
    public DialogRowViewmodel(Ornament ornament) {
        this.ornament=ornament;
    }

    public void setOrnamentDetails(Ornament ornament) {
        this.ornament = ornament;
        notifyChange();
    }



    public String getType() {
        type=ornament.getOrnamentType();
        return type;
    }

    public String getNos() {
        nos=ornament.getNoOfOrnaments();
        return nos;
    }

    public String getGrossWeight() {
        grossWeight=ornament.getGrossWeight();
        return grossWeight;
    }

    public String getLess() {
        less=ornament.getLess();
        return less;
    }

    public String getNetWeight() {
        netWeight=ornament.getNetWeight();
        return netWeight;
    }
}
