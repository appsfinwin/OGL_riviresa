package com.riviresa.custmate.ogl.contact;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.contact.action.ContactsAction;
import com.riviresa.custmate.ogl.contact.pojo.branch.BranchResponse;
import com.riviresa.custmate.ogl.contact.pojo.contact.ContactResponse;
import com.riviresa.custmate.ogl.retrofit.ApiInterface;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ContactRepository {
    public static ContactRepository instance;
    public static ContactRepository getInstance() {
        if (instance == null) {
            instance = new ContactRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<ContactsAction> mAction;

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setmAction(MutableLiveData<ContactsAction> mAction) {
        this.mAction = mAction;
    }

    public MutableLiveData<ContactsAction> getmAction() {
        return mAction;
    }

    @SuppressLint("CheckResult")
    public void getBranch(ApiInterface apiInterface)
    {
        Single<BranchResponse> call=apiInterface.getBranch();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BranchResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(BranchResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new ContactsAction(ContactsAction.BRANCH_SUCCESS, response));

                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, e.getMessage()));
                        }
                    }
                });

    }

    public void getContact(ApiInterface apiInterface, RequestBody body) {
        Single<ContactResponse> call=apiInterface.getContact(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ContactResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ContactResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new ContactsAction(ContactsAction.CONTACT_SUCCESS, response));

                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, e.getMessage()));
                        }
                    }
                });

    }

    public void getBranchList(ApiInterface apiInterface, RequestBody body) {
        Single<ContactResponse> call=apiInterface.getContact(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ContactResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ContactResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new ContactsAction(ContactsAction.BRANCH_LIST_SUCCESS, response));

                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new ContactsAction(ContactsAction.API_ERROR, e.getMessage()));
                        }
                    }
                });

    }
}
