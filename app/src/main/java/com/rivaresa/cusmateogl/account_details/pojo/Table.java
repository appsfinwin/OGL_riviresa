package com.rivaresa.cusmateogl.account_details.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

@SerializedName("CustBankId")
@Expose
private String custBankId;
@SerializedName("Acc_No")
@Expose
private String accNo;
@SerializedName("IFSC_Code")
@Expose
private String iFSCCode;
@SerializedName("MICR")
@Expose
private String micr;
@SerializedName("Bank")
@Expose
private String bank;
@SerializedName("Branch")
@Expose
private String branch;
@SerializedName("Is_Default")
@Expose
private String isDefault;

public String getCustBankId() {
return custBankId;
}

public void setCustBankId(String custBankId) {
this.custBankId = custBankId;
}

public String getAccNo() {
return accNo;
}

public void setAccNo(String accNo) {
this.accNo = accNo;
}

public String getIFSCCode() {
return iFSCCode;
}

public void setIFSCCode(String iFSCCode) {
this.iFSCCode = iFSCCode;
}

public String getMicr() {
return micr;
}

public void setMicr(String micr) {
this.micr = micr;
}

public String getBank() {
return bank;
}

public void setBank(String bank) {
this.bank = bank;
}

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public String getIsDefault() {
return isDefault;
}

public void setIsDefault(String isDefault) {
this.isDefault = isDefault;
}

}