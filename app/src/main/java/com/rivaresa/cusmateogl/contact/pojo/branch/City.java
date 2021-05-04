package com.rivaresa.cusmateogl.contact.pojo.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

@SerializedName("name")
@Expose
private String name;
@SerializedName("state_code")
@Expose
private String stateCode;
@SerializedName("code")
@Expose
private String code;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getStateCode() {
return stateCode;
}

public void setStateCode(String stateCode) {
this.stateCode = stateCode;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

    public City(String name, String stateCode, String code) {
        this.name = name;
        this.stateCode = stateCode;
        this.code = code;
    }
}