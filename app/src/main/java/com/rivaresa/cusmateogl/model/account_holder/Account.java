package com.rivaresa.cusmateogl.model.account_holder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {

@SerializedName("data")
@Expose
private Data data;

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}