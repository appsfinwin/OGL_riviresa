package com.rivaresa.cusmateogl.account_list.pojo.account_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountData {

@SerializedName("AccountNo")
@Expose
private String accountNo;
@SerializedName("InventoryNo")
@Expose
private String inventoryNo;
@SerializedName("Amount")
@Expose
private String amount;
@SerializedName("Item_Weight")
@Expose
private String itemWeight;

public String getAccountNo() {
return accountNo;
}

public void setAccountNo(String accountNo) {
this.accountNo = accountNo;
}

public String getInventoryNo() {
return inventoryNo;
}

public void setInventoryNo(String inventoryNo) {
this.inventoryNo = inventoryNo;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getItemWeight() {
return itemWeight;
}

public void setItemWeight(String itemWeight) {
this.itemWeight = itemWeight;
}

}