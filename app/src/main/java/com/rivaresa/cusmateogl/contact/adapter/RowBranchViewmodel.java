package com.rivaresa.cusmateogl.contact.adapter;

import androidx.databinding.BaseObservable;

import com.rivaresa.cusmateogl.contact.pojo.contact.Contact;

public class RowBranchViewmodel extends BaseObservable {
    Contact contact;
    String branchName,branchAddress,branchPhone,branchEmail;

    public RowBranchViewmodel(Contact contact) {
        this.contact = contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getBranchName() {
        branchName=contact.getBranchName();
        return branchName;
    }

    public String getBranchAddress() {
        branchAddress=contact.getBranchAddresss();
        return branchAddress;
    }

    public String getBranchPhone() {
        branchPhone=contact.getBranchPhone();
        return branchPhone;
    }

    public String getBranchEmail() {
        branchEmail=contact.getBranchEmail();
        return branchEmail;
    }
}
