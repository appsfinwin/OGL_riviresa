package com.rivaresa.cusmateogl.account_list.pojo.inventory_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("Inventory_No")
@Expose
private String inventoryNo;
@SerializedName("Image")
@Expose
private String image;

public String getInventoryNo() {
return inventoryNo;
}

public void setInventoryNo(String inventoryNo) {
this.inventoryNo = inventoryNo;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}