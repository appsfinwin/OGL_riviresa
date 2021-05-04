package com.rivaresa.cusmateogl.contact.action;

import com.rivaresa.cusmateogl.contact.pojo.branch.BranchResponse;
import com.rivaresa.cusmateogl.contact.pojo.contact.ContactResponse;

public class ContactsAction {

    public static final int DEFAULT=0;
    public static final int BRANCH_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CONTACT_SUCCESS=3;
    public static final int CONTACT_ERROR=4;
    public static final int BRANCH_LIST_SUCCESS=5;
    public static final int BRANCH_LIST_ERROR=5;

    public int action;
    public String error;
    public BranchResponse branchResponse;
    public ContactResponse contactResponse;

    public ContactsAction(int action, ContactResponse contactResponse) {
        this.action = action;
        this.contactResponse = contactResponse;
    }

    public ContactsAction(int action, BranchResponse branchResponse) {
        this.action = action;
        this.branchResponse = branchResponse;
    }

    public ContactsAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public ContactsAction(int action) {
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

    public BranchResponse getBranchResponse() {
        return branchResponse;
    }

    public void setBranchResponse(BranchResponse branchResponse) {
        this.branchResponse = branchResponse;
    }

    public ContactResponse getContactResponse() {
        return contactResponse;
    }

    public void setContactResponse(ContactResponse contactResponse) {
        this.contactResponse = contactResponse;
    }
}
