package com.rivaresa.cusmateogl.final_confirmation.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsConditions {

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