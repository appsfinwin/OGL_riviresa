package com.rivaresa.cusmateogl.final_confirmation.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplictaionDetailsResponse {

    @SerializedName("personal_details")
    @Expose
    private PersonalDetails personalDetails;
    @SerializedName("inventory_details")
    @Expose
    private InventoryDetails inventoryDetails;
    @SerializedName("pledge_details")
    @Expose
    private PledgeDetails pledgeDetails;
    @SerializedName("terms_conditions")
    @Expose
    private TermsConditions termsConditions;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public InventoryDetails getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(InventoryDetails inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }

    public PledgeDetails getPledgeDetails() {
        return pledgeDetails;
    }

    public void setPledgeDetails(PledgeDetails pledgeDetails) {
        this.pledgeDetails = pledgeDetails;
    }

    public TermsConditions getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(TermsConditions termsConditions) {
        this.termsConditions = termsConditions;
    }

}