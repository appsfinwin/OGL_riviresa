package com.rivaresa.cusmateogl.contact.pojo.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

@SerializedName("name")
@Expose
private String name;
@SerializedName("country_code")
@Expose
private String countryCode;
@SerializedName("code")
@Expose
private String code;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCountryCode() {
return countryCode;
}

public void setCountryCode(String countryCode) {
this.countryCode = countryCode;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

    public State(String name, String countryCode, String code) {
        this.name = name;
        this.countryCode = countryCode;
        this.code = code;
    }
}