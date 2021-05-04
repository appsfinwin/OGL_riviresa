package com.rivaresa.cusmateogl.account_list.pojo.inventory_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ornament {

@SerializedName("Ornament_Type")
@Expose
private String ornamentType;
@SerializedName("NoOf_Ornaments")
@Expose
private String noOfOrnaments;
@SerializedName("Gross_Weight")
@Expose
private String grossWeight;
@SerializedName("Less")
@Expose
private String less;
@SerializedName("Net_Weight")
@Expose
private String netWeight;

public String getOrnamentType() {
return ornamentType;
}

public void setOrnamentType(String ornamentType) {
this.ornamentType = ornamentType;
}

public String getNoOfOrnaments() {
return noOfOrnaments;
}

public void setNoOfOrnaments(String noOfOrnaments) {
this.noOfOrnaments = noOfOrnaments;
}

public String getGrossWeight() {
return grossWeight;
}

public void setGrossWeight(String grossWeight) {
this.grossWeight = grossWeight;
}

public String getLess() {
return less;
}

public void setLess(String less) {
this.less = less;
}

public String getNetWeight() {
return netWeight;
}

public void setNetWeight(String netWeight) {
this.netWeight = netWeight;
}

}