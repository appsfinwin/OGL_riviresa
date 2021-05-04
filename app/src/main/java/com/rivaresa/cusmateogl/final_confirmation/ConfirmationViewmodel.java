package com.rivaresa.cusmateogl.final_confirmation;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.final_confirmation.action.ConfirmationAction;
import com.rivaresa.cusmateogl.final_confirmation.pojo.ApplictaionDetailsResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ConfirmationViewmodel extends AndroidViewModel {
    public ConfirmationViewmodel(@NonNull Application application) {
        super(application);

        this.application=application;
        compositeDisposable=new CompositeDisposable();
        mAction=new MutableLiveData<>();

        repository=ConfirmationRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);

    }

    public ObservableField<String> customerId=new ObservableField<>("");
    public ObservableField<String> name=new ObservableField<>("");
    public ObservableField<String> address=new ObservableField<>("");
    public ObservableField<String> postOffice=new ObservableField<>("");
    public ObservableField<String> district=new ObservableField<>("");
    public ObservableField<String> mobileNUmber=new ObservableField<>("");
    public ObservableField<String> emailId=new ObservableField<>("");
    public ObservableField<String> inventoryId=new ObservableField<>("");
    public ObservableField<String> itemCount=new ObservableField<>("");
    public ObservableField<String> actualWeight=new ObservableField<>("");
    public ObservableField<String> stoneWeight=new ObservableField<>("");
    public ObservableField<String> netWeight=new ObservableField<>("");
    public ObservableField<String> schemeName=new ObservableField<>("");
    public ObservableField<String> ratePerGram=new ObservableField<>("");
    public ObservableField<String> period=new ObservableField<>("");
    public ObservableField<String> interest=new ObservableField<>("");
    public ObservableField<String> eligibleLoan=new ObservableField<>("");
    public ObservableField<String> loanAmount=new ObservableField<>("");
    public ObservableField<String> termsAndCondition=new ObservableField<>("");

    Application application;
    MutableLiveData<ConfirmationAction> mAction;
    CompositeDisposable compositeDisposable;
    ConfirmationRepository repository;
    ApiInterface apiInterface;
    public LiveData<ConfirmationAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }

    public void setData(ApplictaionDetailsResponse response)
    {
        customerId.set(response.getPersonalDetails().getCustomerId());
        name.set(response.getPersonalDetails().getName());
        address.set(response.getPersonalDetails().getAddress());
        postOffice.set(response.getPersonalDetails().getPostOffice());
        district.set(response.getPersonalDetails().getPlace());
        mobileNUmber.set(response.getPersonalDetails().getMobileNumber());

        inventoryId.set(response.getInventoryDetails().getInventoryId());
        itemCount.set(response.getInventoryDetails().getItemCount());
        actualWeight.set(response.getInventoryDetails().getActualWeight());
        stoneWeight.set(response.getInventoryDetails().getStoneWeight());
        netWeight.set(response.getInventoryDetails().getNetWeight());

        schemeName.set(response.getPledgeDetails().getSchemeName());
        ratePerGram.set(response.getPledgeDetails().getRateGram());
        period.set(response.getPledgeDetails().getPeriodDays());
        interest.set(response.getPledgeDetails().getInterest());
        eligibleLoan.set(response.getPledgeDetails().getEligibleLoan());
        loanAmount.set(response.getPledgeDetails().getLoanAmount());

        termsAndCondition.set(response.getTermsConditions().getTermsConditions());
    }

    public void getAppliactionDetails(String inventoryNo,String schemeID,String customerId,String loanAmount) {
        Map<String, Object> jsonParams = new HashMap<>();
        //jsonParams.put("cust_id", sharedPreferences.getString("cust_id",""));
        jsonParams.put("inventoryno", inventoryNo);
        jsonParams.put("scheme", schemeID);
        jsonParams.put("customerid", customerId);
        jsonParams.put("loanamount", loanAmount);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String param=(new JSONObject(jsonParams)).toString();

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getAppliactionDetails(apiInterface,body);
    }

    public void reset() {
        mAction.setValue(new ConfirmationAction(ConfirmationAction.DEFAULT));
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

    public void clickConfirm(View view)
    {
        mAction.setValue(new ConfirmationAction(ConfirmationAction.CLICK_CONFIRM));
    }

    public void clickCancel(View view)
    {
        mAction.setValue(new ConfirmationAction(ConfirmationAction.CLICK_CANCEL));
    }

    public void clickSettings(View view)
    {
        mAction.setValue(new ConfirmationAction(ConfirmationAction.CLICK_SETTINGS));
    }
}
