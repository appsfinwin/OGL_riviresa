package com.rivaresa.cusmateogl.account_list.action;

import com.rivaresa.cusmateogl.account_list.pojo.account_response.AccountDetailsResponse;
import com.rivaresa.cusmateogl.account_list.pojo.inventory_response.InventoryDetailResponse;

public class AccountListAction {

    public static final int DEFAULT=0;
    public static final int ACCOUNT_DETAILS_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int INVENTORY_DETAILS_SUCCESS=3;
    public static final int CLICK_SETTINGS=4;

    int action;
    String error;

    public InventoryDetailResponse inventoryDetailResponse;

    public AccountListAction(int action, InventoryDetailResponse inventoryDetailResponse) {
        this.action = action;
        this.inventoryDetailResponse = inventoryDetailResponse;
    }

    public InventoryDetailResponse getInventoryDetailResponse() {
        return inventoryDetailResponse;
    }

    public void setInventoryDetailResponse(InventoryDetailResponse inventoryDetailResponse) {
        this.inventoryDetailResponse = inventoryDetailResponse;
    }

    public AccountListAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public AccountDetailsResponse accountDetailsResponse;

    public AccountDetailsResponse getAccountDetailsResponse() {
        return accountDetailsResponse;
    }

    public AccountListAction(int action, AccountDetailsResponse accountDetailsResponse) {
        this.action = action;
        this.accountDetailsResponse = accountDetailsResponse;
    }

    public void setAccountDetailsResponse(AccountDetailsResponse accountDetailsResponse) {
        this.accountDetailsResponse = accountDetailsResponse;
    }

    public AccountListAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
