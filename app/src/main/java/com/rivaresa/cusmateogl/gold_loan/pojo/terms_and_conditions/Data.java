package com.rivaresa.cusmateogl.gold_loan.pojo.terms_and_conditions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("terms_conditions")
@Expose
private String termsConditions;

public String getTermsConditions() {
return termsConditions;
}

public void setTermsConditions(String termsConditions) {
this.termsConditions = termsConditions;
}

}