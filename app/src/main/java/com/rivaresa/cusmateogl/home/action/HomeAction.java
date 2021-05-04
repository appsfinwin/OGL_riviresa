package com.rivaresa.cusmateogl.home.action;

public class HomeAction {

    public static final int DEFAULT=0;
    public static final int CLICK_PAY_ONLINE=1;
    public static final int CLICK_ACCOUNT_DETAILS=2;
    public static final int CLICK_ONLINE_GOLD_LOAN=3;
    public static final int CLICK_CONTACT=4;

    int action;
    String error;

    public HomeAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
