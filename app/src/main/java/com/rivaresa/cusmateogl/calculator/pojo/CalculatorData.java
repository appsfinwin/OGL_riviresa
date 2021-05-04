package com.rivaresa.cusmateogl.calculator.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculatorData {

@SerializedName("Days")
@Expose
private String days;
@SerializedName("Interest")
@Expose
private Integer interest;
@SerializedName("Amount")
@Expose
private Double amount;

public String getDays() {
return days;
}

public void setDays(String days) {
this.days = days;
}

public Integer getInterest() {
return interest;
}

public void setInterest(Integer interest) {
this.interest = interest;
}

public Double getAmount() {
return amount;
}

public void setAmount(Double amount) {
this.amount = amount;
}

}