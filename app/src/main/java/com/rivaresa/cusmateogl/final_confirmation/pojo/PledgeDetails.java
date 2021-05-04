package com.rivaresa.cusmateogl.final_confirmation.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PledgeDetails {

@SerializedName("eligible_loan")
@Expose
private String eligibleLoan;
@SerializedName("scheme_name")
@Expose
private String schemeName;
@SerializedName("rate_gram")
@Expose
private String rateGram;
@SerializedName("period_days")
@Expose
private String periodDays;
@SerializedName("interest")
@Expose
private String interest;
@SerializedName("loan_amount")
@Expose
private String loanAmount;

public String getEligibleLoan() {
return eligibleLoan;
}

public void setEligibleLoan(String eligibleLoan) {
this.eligibleLoan = eligibleLoan;
}

public String getSchemeName() {
return schemeName;
}

public void setSchemeName(String schemeName) {
this.schemeName = schemeName;
}

public String getRateGram() {
return rateGram;
}

public void setRateGram(String rateGram) {
this.rateGram = rateGram;
}

public String getPeriodDays() {
return periodDays;
}

public void setPeriodDays(String periodDays) {
this.periodDays = periodDays;
}

public String getInterest() {
return interest;
}

public void setInterest(String interest) {
this.interest = interest;
}

public String getLoanAmount() {
return loanAmount;
}

public void setLoanAmount(String loanAmount) {
this.loanAmount = loanAmount;
}

}