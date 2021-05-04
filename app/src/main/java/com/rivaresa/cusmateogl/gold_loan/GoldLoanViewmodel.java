package com.rivaresa.cusmateogl.gold_loan;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.pojo.Table;
import com.rivaresa.cusmateogl.databinding.ActivityGoldLoanBinding;
import com.rivaresa.cusmateogl.gold_loan.action.GoldLoanAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GoldLoanViewmodel extends AndroidViewModel {
    public GoldLoanViewmodel(@NonNull Application application) {
        super(application);
        this.application = application;
        mAction = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        repository = GoldLoanRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //    public void setInitData(Table bankDetails) {
//
//        binding.tvAccountNumber.setText(bankDetails.getAccNo());
//        binding.tvBankName.setText(bankDetails.getBank());
//        binding.tvIfsc.setText(bankDetails.getIFSCCode());
//        binding.tvBranch.setText(bankDetails.getBranch());
//        binding.tvName.setText(sharedPreferences.getString("name", ""));
//        binding.tvEmail.setText(sharedPreferences.getString("email", ""));
//        binding.tvMobile.setText(sharedPreferences.getString("phone", ""));
//        editor.putString("CustBankId",bankDetails.getCustBankId());
//        editor.commit();
//    }
    ObservableField<List<Table>> bankDetailsList = new ObservableField<>();

    public void setInitData(List<Table> bankDetails) {
        bankDetailsList.set(bankDetails);
        for (Table table : bankDetails) {
            if (table.getIsDefault().equals("Y")) {
                binding.tvAccountNumber.setText(table.getAccNo());
                binding.tvBankName.setText(table.getBank());
                binding.tvIfsc.setText(table.getIFSCCode());
                binding.tvBranch.setText(table.getBranch());
                binding.tvName.setText(sharedPreferences.getString("name", ""));
                binding.tvEmail.setText(sharedPreferences.getString("email", ""));
                binding.tvMobile.setText(sharedPreferences.getString("phone", ""));

                editor.putString("CustBankId", table.getCustBankId());
                editor.putString("bankAccountNumber", table.getAccNo());
                editor.putString("bankIfsc", table.getIFSCCode());
                editor.commit();
            }

        }


    }

    public void getBankDetails() {


        Map<String, String> items = new HashMap<>();

        items.put("CustId", sharedPreferences.getString("cust_id", ""));

        //params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(items)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getBankDetails(apiInterface, body);
    }

    public ObservableBoolean isChecked = new ObservableBoolean(false);
    ActivityGoldLoanBinding binding;
    Application application;
    CompositeDisposable compositeDisposable;
    GoldLoanRepository repository;
    MutableLiveData<GoldLoanAction> mAction;
    Activity activity;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;

    public LiveData<GoldLoanAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCheckedChange(CompoundButton button, boolean check) {
        if (check) {
            initLoading(context);
            getTermsAndConditions();
            binding.btnContinue.setEnabled(true);
            binding.btnContinue.setBackgroundColor(getApplication().getColor(R.color.white));
        } else {
            binding.btnContinue.setBackgroundColor(getApplication().getColor(R.color.gray));
            binding.btnContinue.setEnabled(false);
        }
    }

    public void setBinding(ActivityGoldLoanBinding binding) {
        this.binding = binding;
    }

    public void clickContinue(View view) {
        if (isChecked.get()) {
            mAction.setValue(new GoldLoanAction(GoldLoanAction.CLICK_CONTINUE));
        } else {
        }
    }

    public void clickChangeAccount(View view) {

        mAction.setValue(new GoldLoanAction(GoldLoanAction.CLICK_CHANGE_ACCOUNT));

    }

    public void getTermsAndConditions() {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getTerms(apiInterface);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();

    }

    Dialog loading;


    public void initLoading(Context context) {
        loading = Services.showLoading(context);
    }

    public void cancelLoading() {
        if (loading != null) {
            loading.cancel();
            loading = null;
        }
    }

    public void setContext(GoldLoanActivity goldLoanActivity) {
        this.context = goldLoanActivity;
    }

    public void setBankData(Table bankDetail) {
        binding.tvAccountNumber.setText(bankDetail.getAccNo());
        binding.tvBankName.setText(bankDetail.getBank());
        binding.tvIfsc.setText(bankDetail.getIFSCCode());
        binding.tvBranch.setText(bankDetail.getBranch());
        binding.tvName.setText(sharedPreferences.getString("name", ""));
        binding.tvEmail.setText(sharedPreferences.getString("email", ""));
        binding.tvMobile.setText(sharedPreferences.getString("phone", ""));
        editor.putString("CustBankId", bankDetail.getCustBankId());
        editor.putString("bankAccountNumber", bankDetail.getAccNo());
        editor.putString("bankIfsc", bankDetail.getIFSCCode());
        editor.commit();
    }
}
