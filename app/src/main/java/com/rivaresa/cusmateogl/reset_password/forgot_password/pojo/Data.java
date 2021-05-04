package com.rivaresa.cusmateogl.reset_password.forgot_password.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("Table1")
@Expose
private List<Table1> table1 = null;

public List<Table1> getTable1() {
return table1;
}

public void setTable1(List<Table1> table1) {
this.table1 = table1;
}

}