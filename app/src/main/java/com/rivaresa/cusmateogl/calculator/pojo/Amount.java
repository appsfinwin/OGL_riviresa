package com.rivaresa.cusmateogl.calculator.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount {

@SerializedName("totaldays")
@Expose
private String totaldays;
@SerializedName("totalamount")
@Expose
private String totalamount;

public String getTotaldays() {
return totaldays;
}

public void setTotaldays(String totaldays) {
this.totaldays = totaldays;
}

public String getTotalamount() {
return totalamount;
}

public void setTotalamount(String totalamount) {
this.totalamount = totalamount;
}

}