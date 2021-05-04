package com.rivaresa.cusmateogl.contact.pojo.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

@SerializedName("country")
@Expose
private List<Country> country = null;
@SerializedName("state")
@Expose
private List<State> state = null;
@SerializedName("city")
@Expose
private List<City> city = null;

public List<Country> getCountry() {
return country;
}

public void setCountry(List<Country> country) {
this.country = country;
}

public List<State> getState() {
return state;
}

public void setState(List<State> state) {
this.state = state;
}

public List<City> getCity() {
return city;
}

public void setCity(List<City> city) {
this.city = city;
}

}