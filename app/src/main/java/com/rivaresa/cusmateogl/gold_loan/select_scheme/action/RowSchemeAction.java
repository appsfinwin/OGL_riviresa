package com.rivaresa.cusmateogl.gold_loan.select_scheme.action;

import com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo.SchemeData;

public class RowSchemeAction {
    int action;
    public static final int CLICK_SELECT=1;
    public static final int CLICK_SETTLEMENT=2;

    public SchemeData schemeData;

    public RowSchemeAction(int action, SchemeData schemeData) {
        this.action = action;
        this.schemeData = schemeData;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public SchemeData getSchemeData() {
        return schemeData;
    }

    public void setSchemeData(SchemeData schemeData) {
        this.schemeData = schemeData;
    }
}
