package com.rivaresa.cusmateogl.account_details.closed_loans.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoansData {

@SerializedName("Scheme")
@Expose
private String scheme;
@SerializedName("Branch")
@Expose
private String branch;
@SerializedName("Loan_amount")
@Expose
private Integer loanAmount;
@SerializedName("Period")
@Expose
private Integer period;
@SerializedName("Period_type")
@Expose
private String periodType;
@SerializedName("Ornaments_released")
@Expose
private String ornamentsReleased;
@SerializedName("Ornaments_released_date")
@Expose
private String ornamentsReleasedDate;
@SerializedName("AccountNo")
@Expose
private String accountNo;
@SerializedName("InventoryNo")
@Expose
private String inventoryNo;

public String getScheme() {
return scheme;
}

public void setScheme(String scheme) {
this.scheme = scheme;
}

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public Integer getLoanAmount() {
return loanAmount;
}

public void setLoanAmount(Integer loanAmount) {
this.loanAmount = loanAmount;
}

public Integer getPeriod() {
return period;
}

public void setPeriod(Integer period) {
this.period = period;
}

public String getPeriodType() {
return periodType;
}

public void setPeriodType(String periodType) {
this.periodType = periodType;
}

public String getOrnamentsReleased() {
return ornamentsReleased;
}

public void setOrnamentsReleased(String ornamentsReleased) {
this.ornamentsReleased = ornamentsReleased;
}

public String getOrnamentsReleasedDate() {
return ornamentsReleasedDate;
}

public void setOrnamentsReleasedDate(String ornamentsReleasedDate) {
this.ornamentsReleasedDate = ornamentsReleasedDate;
}

public String getAccountNo() {
return accountNo;
}

public void setAccountNo(String accountNo) {
this.accountNo = accountNo;
}

public String getInventoryNo() {
return inventoryNo;
}

public void setInventoryNo(String inventoryNo) {
this.inventoryNo = inventoryNo;
}

    public LoansData() {
    }
}