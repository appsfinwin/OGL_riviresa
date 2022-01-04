package com.riviresa.custmate.ogl.payment.paytm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riviresa.custmate.R;
import com.riviresa.custmate.databinding.ActivityPaymentBinding;
import com.riviresa.custmate.ogl.BaseActivity;
import com.riviresa.custmate.ogl.home.HomeActivity;
import com.riviresa.custmate.ogl.payment.action.PaymentAction;
import com.riviresa.custmate.ogl.payment.paytm.adapter.PaymentsAdapter;
import com.riviresa.custmate.ogl.payment.razor_pay.RazorPayActivity;
import com.riviresa.custmate.ogl.utils.Services;


public class PaymentActivity extends BaseActivity {

    PaymentViewmodel viewmodel;
    ActivityPaymentBinding binding;
    String payment_type, account_number, flag, payment = "";
    PaymentsAdapter adapter;
    private static final String TAG = "PaymentActivity";
    Intent paytmIntent;
    SharedPreferences sharedPreferences;

    public static final String PAYTM_APP_PACKAGE = "net.one97.paytm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        viewmodel = new ViewModelProvider(this).get(PaymentViewmodel.class);
        binding.setViewmodel(viewmodel);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);


        viewmodel.setBinding(binding);
        Intent intent = getIntent();
        account_number = sharedPreferences.getString("account_number", "");
        selectInterestPayment();
//        payment_type = intent.getStringExtra("payment_type");
//        if (!payment_type.equals("")) {
//            getPaymentType(payment_type);
//            viewmodel.paymentMode.set(payment_type);
//        }


        // Toast.makeText(this, payment_type, Toast.LENGTH_SHORT).show();
        setupRecyclerView(binding.rvPayment);

        binding.tvInterestPayment.setOnClickListener(v -> {
            selectInterestPayment();
        });

        binding.tvPartPayment.setOnClickListener(v -> {
            selectPartPayment();
        });

        //aytmSdk();
        viewmodel.getmAction().observe(this, new Observer<PaymentAction>() {
            @Override
            public void onChanged(PaymentAction paymentAction) {
                viewmodel.cancelLoading();
                switch (paymentAction.getAction()) {
                    case PaymentAction.API_SUCCESS:

                        adapter.setSettlementDataList(paymentAction.settlementDetailsResponse.getData());
                        adapter.notifyDataSetChanged();

                        viewmodel.setBalance(paymentAction.getSettlementDetailsResponse().getData());
                        if (payment_type.equals("interest_payment")) {
                            viewmodel.setAmount(paymentAction.getSettlementDetailsResponse().getData());
                        } else if (payment_type.equals("full_payment")) {
                            viewmodel.setFullAmount();
                        }
                        break;


                    case PaymentAction.API_ERROR:
                        Services.errorDialog(PaymentActivity.this, paymentAction.getError());
                        break;

                    case PaymentAction.CHECKSUM_SUCCESS:
                        //viewmodel.setChecksum();

                        //paytm(paymentAction.getChecksumResponse().getData());
//                            String currentAppVersion=getPaytmVersion(PaymentActivity.this);
//                            if (versionCompare(currentAppVersion, "8.6.0") < 0) {
//                                //Full screen App Invoke flow
//
//                                Intent paytmIntent = new Intent();
//                                Bundle bundle = new Bundle();
//                                bundle.putDouble("nativeSdkForMerchantAmount", 1);
//                                bundle.putString("orderid", "202004251336551111");
//                               // bundle.putString("txnToken", paymentAction.getChecksumResponse().getData());
//                                bundle.putString("txnToken", "DLQQHUXbf+JN0oXswiE6LeLdfRG+b5JUOBT03vAQOe2mEPnvjBze/K86NTaIJ0c53prK39PjkXDSujUycnehrVdhBIJFujRa+S0taI2XIJc=");
//                                bundle.putString("mid", "sYwTAG93503854487535");
//                                paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRJarvisSplash"));
//                                paytmIntent.putExtra("paymentmode", 2); // You must have to pass hard coded 2 here, Else your transaction would not proceed.
//                                paytmIntent.putExtra("bill", bundle);
//                                startActivityForResult(paytmIntent, 144);
//                            }else
//                                {
//                               // New App Invoke flow
//
//                                Intent paytmIntent = new Intent();
//                                paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRRechargePaymentActivity"));
//                                paytmIntent.putExtra("paymentmode", 2);
//                                paytmIntent.putExtra("enable_paytm_invoke", true);
//                                paytmIntent.putExtra("paytm_invoke", true);
//                                paytmIntent.putExtra("price", "1"); //this is string amount
//                                paytmIntent.putExtra("nativeSdkEnabled", true);
//                                paytmIntent.putExtra("orderid", "202004251336551111");
//                                //paytmIntent.putExtra("txnToken", paymentAction.getChecksumResponse().getData());
//                                    paytmIntent.putExtra("txnToken", "DLQQHUXbf+JN0oXswiE6LeLdfRG+b5JUOBT03vAQOe2mEPnvjBze/K86NTaIJ0c53prK39PjkXDSujUycnehrVdhBIJFujRa+S0taI2XIJc=");
//
//                                    paytmIntent.putExtra("mid", "sYwTAG93503854487535");
//                                startActivityForResult(paytmIntent, 144);
//
//                            }
                        break;

                    case PaymentAction.CLICK_PAY:
//                        Intent payIntent=new Intent(PaymentActivity.this,PayUActivity.class);
//                        payIntent.putExtra("amount",viewmodel.anountToPay.get());
//                        payIntent.putExtra("flag",flag);
//                        startActivity(payIntent);

//                        Intent intent=new Intent(getApplicationContext(), AxisPaymentActivity.class);
//                        intent.putExtra("net_amount",viewmodel.amountToPay.get());
//                        intent.putExtra("flag",flag);
//                        intent.putExtra("from","pay_online");
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


                        Intent intent = new Intent(getApplicationContext(), RazorPayActivity.class);
                        intent.putExtra("net_amount", viewmodel.amountToPay.get());
                        intent.putExtra("flag", flag);
                        intent.putExtra("from", "pay_online");
                        intent.putExtra("paymentType", payment);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        //startPayment();
                        break;

                    case PaymentAction.CLICK_SETTINGS:
                        exitDialog();
                        break;
                }
            }
        });


    }

    private void selectPartPayment() {
        binding.tvPartPayment.setBackgroundColor(getResources().getColor(R.color.white));
        binding.tvInterestPayment.setBackgroundColor(getResources().getColor(R.color.l_gray));
        binding.tvPartPayment.setElevation(3);
        binding.tvInterestPayment.setElevation(0);
        viewmodel.paymentMode.set("part_payment");
        getPaymentType("part_payment");
        payment_type = "part_payment";
    }

    private void selectInterestPayment() {
        binding.tvInterestPayment.setBackgroundColor(getResources().getColor(R.color.white));
        binding.tvPartPayment.setBackgroundColor(getResources().getColor(R.color.l_gray));
        binding.tvInterestPayment.setElevation(3);
        binding.tvPartPayment.setElevation(0);
        viewmodel.paymentMode.set("interest_payment");
        getPaymentType("interest_payment");
        payment_type = "interest_payment";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 144 && data != null) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.d(TAG, "paytm_key " + key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            Log.d(TAG, " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.d(TAG, " data response - " + data.getStringExtra("response"));


            //String da=data.getData().toString();
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage"), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra("response"), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaymentType(String payment_type) {
        switch (payment_type) {
            case "interest_payment":
                flag = "Receipt";
                payment = "Interest Payment";
                binding.layoutAmount.setVisibility(View.GONE);
                binding.layoutInterest.setVisibility(View.VISIBLE);
                binding.tvHeading.setText("INTEREST PAYMENT");
                viewmodel.initLoading(PaymentActivity.this);
                viewmodel.getSettlementDetails("", account_number);

                break;

            case "part_payment":
                flag = "Receipt";
                payment = "Part Payment";
                binding.tvHeading.setText("PART PAYMENT");
                binding.layoutAmount.setVisibility(View.VISIBLE);
                viewmodel.amountToPay.set("");
                binding.layoutInterest.setVisibility(View.GONE);
                viewmodel.initLoading(PaymentActivity.this);
                viewmodel.getSettlementDetails("", account_number);

                break;

            case "full_payment":
                flag = "Closing";
                payment = "Full Payment";
                binding.tvHeading.setText("FULL PAYMENT");
                binding.layoutAmount.setVisibility(View.GONE);
                binding.layoutInterest.setVisibility(View.GONE);
                viewmodel.initLoading(PaymentActivity.this);
                viewmodel.getSettlementDetails("Is_Closing", account_number);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.payment_type);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.reset();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new PaymentsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void exitDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit?")
                .setMessage("Do you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}