package com.rivaresa.cusmateogl.contact.pojo.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }
}