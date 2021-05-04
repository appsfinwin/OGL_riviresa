package com.rivaresa.cusmateogl.account_details.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetBankDetailsResponse {

@SerializedName("bank_details")
@Expose
private BankDetails bankDetails;

public BankDetails getBankDetails() {
return bankDetails;
}

public void setBankDetails(BankDetails bankDetails) {
this.bankDetails = bankDetails;
}

}