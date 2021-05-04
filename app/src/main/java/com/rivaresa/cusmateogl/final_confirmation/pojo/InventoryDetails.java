package com.rivaresa.cusmateogl.final_confirmation.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InventoryDetails {

@SerializedName("inventory_id")
@Expose
private String inventoryId;
@SerializedName("item_count")
@Expose
private String itemCount;
@SerializedName("actual_weight")
@Expose
private String actualWeight;
@SerializedName("stone_weight")
@Expose
private String stoneWeight;
@SerializedName("net_weight")
@Expose
private String netWeight;

public String getInventoryId() {
return inventoryId;
}

public void setInventoryId(String inventoryId) {
this.inventoryId = inventoryId;
}

public String getItemCount() {
return itemCount;
}

public void setItemCount(String itemCount) {
this.itemCount = itemCount;
}

public String getActualWeight() {
return actualWeight;
}

public void setActualWeight(String actualWeight) {
this.actualWeight = actualWeight;
}

public String getStoneWeight() {
return stoneWeight;
}

public void setStoneWeight(String stoneWeight) {
this.stoneWeight = stoneWeight;
}

public String getNetWeight() {
return netWeight;
}

public void setNetWeight(String netWeight) {
this.netWeight = netWeight;
}

}