package com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemeData {

@SerializedName("name")
@Expose
private String name;
@SerializedName("schemecode")
@Expose
private String schemecode;
@SerializedName("period")
@Expose
private String period;
@SerializedName("periodtype")
@Expose
private String periodtype;
@SerializedName("interest")
@Expose
private String interest;
@SerializedName("eligible_amount")
@Expose
private String eligibleAmount;
@SerializedName("settlement_total")
@Expose
private String settlementTotal;
@SerializedName("net_amt_available")
@Expose
private String netAmtAvailable;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getSchemecode() {
return schemecode;
}

public void setSchemecode(String schemecode) {
this.schemecode = schemecode;
}

public String getPeriod() {
return period;
}

public void setPeriod(String period) {
this.period = period;
}

public String getPeriodtype() {
return periodtype;
}

public void setPeriodtype(String periodtype) {
this.periodtype = periodtype;
}

public String getInterest() {
return interest;
}

public void setInterest(String interest) {
this.interest = interest;
}

public String getEligibleAmount() {
return eligibleAmount;
}

public void setEligibleAmount(String eligibleAmount) {
this.eligibleAmount = eligibleAmount;
}

public String getSettlementTotal() {
return settlementTotal;
}

public void setSettlementTotal(String settlementTotal) {
this.settlementTotal = settlementTotal;
}

public String getNetAmtAvailable() {
return netAmtAvailable;
}

public void setNetAmtAvailable(String netAmtAvailable) {
this.netAmtAvailable = netAmtAvailable;
}

}