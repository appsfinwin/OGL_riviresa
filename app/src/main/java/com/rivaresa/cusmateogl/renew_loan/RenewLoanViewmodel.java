package com.rivaresa.cusmateogl.renew_loan;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.renew_loan.action.RenewLoanAction;
import com.rivaresa.cusmateogl.renew_loan.pojo.Data;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RenewLoanViewmodel extends AndroidViewModel {
    public RenewLoanViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository = RenewLoanRepository.getInstance();
        compositeDisposable = new CompositeDisposable();
        mAction = new MutableLiveData<>();

        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);

    }
    Application application;
    ProgressDialog loading;
    CompositeDisposable compositeDisposable;
    MutableLiveData<RenewLoanAction> mAction;
    RenewLoanRepository repository;
    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    public ObservableField<String> transactionId=new ObservableField<>("");
    public ObservableField<String> date=new ObservableField<>("");
    public ObservableField<String> time=new ObservableField<>("");
    public ObservableField<String> oldAccountNumber=new ObservableField<>("");
    public ObservableField<String> newAccountNumber=new ObservableField<>("");
    public ObservableField<String> status=new ObservableField<>("");

    public void renewGoldLoan(String loanAmount, String ifsc,
                              String loanAccountNumber, String schemeCode,
                              String bankAccountNumber, String period,
                              String periodType, String interest,
                              String CustBankId) {

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("amount", loanAmount);
        jsonParams.put("IFSC", ifsc);
        jsonParams.put("lnacno", loanAccountNumber);
        jsonParams.put("scheme", schemeCode);
        jsonParams.put("bankacno", bankAccountNumber);
        jsonParams.put("flag", "LoanRenewal");
        jsonParams.put("period", period);
        jsonParams.put("period_type", periodType);
        jsonParams.put("interest", interest);
        jsonParams.put("ben_id", CustBankId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String param=(new JSONObject(jsonParams)).toString();

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.goldLoanRenewal(apiInterface, body);
    }
    public void renewLoan(String loanAmount, String loanAccountNumber, String flag,String CustBankId) {

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("amount", loanAmount);
        jsonParams.put("IFSC", "");
        jsonParams.put("lnacno", loanAccountNumber);
        jsonParams.put("scheme", "");
        jsonParams.put("bankacno", "");
        jsonParams.put("flag", flag);
        jsonParams.put("period", "");
        jsonParams.put("period_type", "");
        jsonParams.put("interest", "");
        jsonParams.put("ben_id", CustBankId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.goldLoanRenewal(apiInterface, body);
    }

    public LiveData<RenewLoanAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }

    public void setReceipt(Data data) {
        transactionId.set(data.getTranid());
        date.set(data.getDate());
        time.set(data.getTime());
        oldAccountNumber.set(data.getAccountNo());
        newAccountNumber.set(data.getMessage());
        status.set("TRANSACTION SUCCESS");
    }

    public void setGoldLoanReceipt(com.rivaresa.cusmateogl.renew_loan.pojo.gold_loan.Data data) {
        if (data.getTable1().size()>0) {
            transactionId.set(data.getTable1().get(0).getTranid());
            date.set(data.getTable1().get(0).getDate());
            time.set(data.getTable1().get(0).getTime());
            oldAccountNumber.set(data.getTable1().get(0).getAccountNo());
            newAccountNumber.set(data.getTable1().get(0).getMessage());
            status.set(data.getTable1().get(0).getMessage());
        }
    }

    public void setTransactionErrorinit()
    {
        status.set("TRANSACTION ERROR");
    }

    public void clickOk(View view)
    {
        mAction.setValue(new RenewLoanAction(RenewLoanAction.CLICK_OK));
    }




    public void initLoading(Context context) {
        loading= Services.showProgressDialog(context);
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
