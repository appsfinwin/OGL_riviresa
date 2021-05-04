package com.rivaresa.cusmateogl.contact.pojo.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

@SerializedName("Branch_name")
@Expose
private String branchName;
@SerializedName("Branch_addresss")
@Expose
private String branchAddresss;
@SerializedName("Branch_phone")
@Expose
private String branchPhone;
@SerializedName("Branch_email")
@Expose
private String branchEmail;

public String getBranchName() {
return branchName;
}

public void setBranchName(String branchName) {
this.branchName = branchName;
}

public String getBranchAddresss() {
return branchAddresss;
}

public void setBranchAddresss(String branchAddresss) {
this.branchAddresss = branchAddresss;
}

public String getBranchPhone() {
return branchPhone;
}

public void setBranchPhone(String branchPhone) {
this.branchPhone = branchPhone;
}

public String getBranchEmail() {
return branchEmail;
}

public void setBranchEmail(String branchEmail) {
this.branchEmail = branchEmail;
}

}