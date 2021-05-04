package com.rivaresa.cusmateogl.payment.paytm.pojo.checksum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecksumResponse {

@SerializedName("data")
@Expose
private String data;

public String getData() {
return data;
}

public void setData(String data) {
this.data = data;
}

}