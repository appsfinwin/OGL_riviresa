package com.riviresa.custmate.ogl.renew_loan.pojo.gold_loan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.riviresa.custmate.ogl.renew_loan.pojo.Table1;

import java.util.List;

public class Data {

    @SerializedName("Table1")
    @Expose
    private List<com.riviresa.custmate.ogl.renew_loan.pojo.Table1> table1 = null;

    public List<com.riviresa.custmate.ogl.renew_loan.pojo.Table1> getTable1() {
        return table1;
    }

    public void setTable1(List<Table1> table1) {
        this.table1 = table1;
    }
}
