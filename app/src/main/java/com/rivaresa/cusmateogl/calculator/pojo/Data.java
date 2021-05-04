package com.rivaresa.cusmateogl.calculator.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("data")
@Expose
private List<CalculatorData> data = null;
@SerializedName("amount")
@Expose
private Amount amount;

public List<CalculatorData> getData() {
return data;
}

public void setData(List<CalculatorData> data) {
this.data = data;
}

public Amount getAmount() {
return amount;
}

public void setAmount(Amount amount) {
this.amount = amount;
}

}