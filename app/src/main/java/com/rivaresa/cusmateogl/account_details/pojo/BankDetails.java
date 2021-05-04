package com.rivaresa.cusmateogl.account_details.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankDetails {

@SerializedName("Table")
@Expose
private List<Table> table = null;

public List<Table> getTable() {
return table;
}

public void setTable(List<Table> table) {
this.table = table;
}

}