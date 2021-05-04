package com.rivaresa.cusmateogl.contact;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.rivaresa.cusmateogl.BR;
import com.rivaresa.cusmateogl.contact.action.ContactsAction;
import com.rivaresa.cusmateogl.contact.pojo.branch.City;
import com.rivaresa.cusmateogl.contact.pojo.branch.Country;
import com.rivaresa.cusmateogl.contact.pojo.branch.Data;
import com.rivaresa.cusmateogl.contact.pojo.branch.State;
import com.rivaresa.cusmateogl.databinding.ActivityContactBinding;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ContactViewmodel extends AndroidViewModel implements Observable {
    public ContactViewmodel(@NonNull Application application) {
        super(application);
        initSpnner();
        this.application=application;
        mAction=new MutableLiveData<>();
        compositeDisposable=new CompositeDisposable();
        repository=ContactRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);

    }
    ActivityContactBinding binding;
    public final ObservableArrayList<String> contries = new ObservableArrayList<>();
    public final ObservableArrayList<String> states = new ObservableArrayList<>();
    public final ObservableArrayList<String> districts = new ObservableArrayList<>();

    public ObservableField<String> contactName=new ObservableField<>("");
    public ObservableField<String> contactAddress=new ObservableField<>("");
    public ObservableField<String> contactMobile=new ObservableField<>("");
    public ObservableField<String> contactEmail=new ObservableField<>("");

    public ObservableField<String> contryCode=new ObservableField<>("-1");
    public ObservableField<String> stateCode=new ObservableField<>("-1");
    public ObservableField<String> cityCode=new ObservableField<>("-1");
    public ObservableField<Integer> showBranch=new ObservableField<>(View.GONE);

    private PropertyChangeRegistry registry = new PropertyChangeRegistry();
    public ApiInterface apiInterface;
    ContactRepository repository;
    CompositeDisposable compositeDisposable;
    MutableLiveData<ContactsAction> mAction;
    List<State> stateyList=new ArrayList<>();
    List<Country> countryList=new ArrayList<>();
    List<City> cityList=new ArrayList<>();
    Application application;
    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }



    private int selectedContry;

    public void setSelectedContry(int selectedContry) {
        this.selectedContry = selectedContry;
        registry.notifyChange(this, BR.selectedContry);
    }



    @Bindable
    public int getSelectedContry() {
        return selectedContry;
    }

    public void onSelecedItemContry(AdapterView<?> parent, View view, int position, long id) {

        showBranch.set(View.GONE);
        contryCode.set(countryList.get(position).getCode());

    }

    private int selectedState;

    @Bindable
    public int getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(int selectedState) {
        this.selectedState = selectedState;
        registry.notifyChange(this,BR.selectedState);
    }
    public void onSelectedState(AdapterView<?> parent,View view,int position,long id)
    {
        showBranch.set(View.GONE);
        stateCode.set(stateyList.get(position).getCode());
    }

    private int selectedDistrict;
    @Bindable
    public int getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(int selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
        registry.notifyChange(this,BR.selectedDistrict);
    }
    public void onSelectedDistrict(AdapterView<?> parent,View view,int position,long id)
    {
        showBranch.set(View.GONE);
        cityCode.set(cityList.get(position).getCode());
    }


    public void getBranch() {
        Map<String, Object> jsonParams = new HashMap<>();
        //jsonParams.put("cust_id", sharedPreferences.getString("cust_id",""));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getBranch(apiInterface);
    }

    public void getContact() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("country", "000");
        jsonParams.put("state", "000");
        jsonParams.put("city", "000");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getContact(apiInterface,body);
    }

    public void getBranchesList(String contry,String state,String city) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("country", contry);
        jsonParams.put("state", state);
        jsonParams.put("city", city);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getBranchList(apiInterface,body);
    }

    public MutableLiveData<ContactsAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void initSpnner()
    {
        contries.add(0,"--Country--");
        states.add(0,"--State--");
        districts.add(0,"--City--");

        countryList.add(new Country("--Country--","-1"));

        stateyList.add(new State("--state--","-1","-1"));

        cityList.add(new City("--City--","-1","-1"));

    }

    public void setSpinnerData(Data data) {
        if (data.getCountry().size()>0) {
            for (Country country : data.getCountry()) {
                contries.add(country.getName());
                countryList.add(country);
            }
        }
        if (data.getState().size()>0) {
            for (State state : data.getState()) {
                states.add(state.getName());
                stateyList.add(state);
            }
        }

        if (data.getCity().size()>0) {
            for (City city : data.getCity()) {
                districts.add(city.getName());
                cityList.add(city);
            }
        }
    }

    public void setContacts(com.rivaresa.cusmateogl.contact.pojo.contact.Data data) {
        if (data.getContact().size()>0)
        {
            contactName.set(data.getContact().get(0).getBranchName());
            contactAddress.set(data.getContact().get(0).getBranchAddresss());
            contactMobile.set(data.getContact().get(0).getBranchPhone());
            contactEmail.set(data.getContact().get(0).getBranchEmail());
        }
    }

    public void clickGetBranch(View view)
    {
        if (contryCode.get().equals("-1") || stateCode.get().equals("-1") || cityCode.get().equals("-1"))
        {
            if (contryCode.get().equals("-1"))
            {
                showSnakbar("Please select a country");
            }
            else if (stateCode.get().equals("-1"))
            {
                showSnakbar("Please select state");
            }else if (cityCode.get().equals("-1"))
            {
                showSnakbar("Please select a city");
            }
        }else {

            initLoading(view.getContext());
            getBranchesList(contryCode.get(),stateCode.get(),cityCode.get());
        }
    }

    private void showSnakbar(String message) {

        Snackbar snackbar = Snackbar
                .make(binding.mainLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public void setBinding(ActivityContactBinding binding) {
        this.binding=binding;
    }


    Dialog loading;


    public void initLoading(Context context) {
        loading= Services.showLoading(context);
    }

    public void cancelLoading()
    {
        if(loading!=null)
        {
            loading.cancel();
            loading=null;
        }
    }
}
