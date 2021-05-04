package com.rivaresa.cusmateogl.payment.paytm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.PaytmUtility;
import com.paytm.pgsdk.TransactionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityPaymentBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.payment.action.PaymentAction;
import com.rivaresa.cusmateogl.payment.axis_payment.AxisPaymentActivity;
import com.rivaresa.cusmateogl.payment.paytm.adapter.PaymentsAdapter;
import com.rivaresa.cusmateogl.renew_loan.RenewLoanActivity;

import org.json.JSONObject;

public class PaymentActivity extends BaseActivity implements PaymentResultListener {

    PaymentViewmodel viewmodel;
    ActivityPaymentBinding binding;
    String payment_type, account_number,flag,payment="";
    PaymentsAdapter adapter;
    private static final String TAG = "PaymentActivity";
    Intent paytmIntent;

    public static final String PAYTM_APP_PACKAGE = "net.one97.paytm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        viewmodel = new ViewModelProvider(this).get(PaymentViewmodel.class);
        binding.setViewmodel(viewmodel);


        viewmodel.setBinding(binding);
        Intent intent = getIntent();
        account_number = intent.getStringExtra("account_number");
        payment_type = intent.getStringExtra("payment_type");
        if (!payment_type.equals("")) {
            getPaymentType(payment_type);
            viewmodel.paymentMode.set(payment_type);
        }


        // Toast.makeText(this, payment_type, Toast.LENGTH_SHORT).show();
        setupRecyclerView(binding.rvPayment);

        //aytmSdk();
       // startPayment();
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
                        }else if (payment_type.equals("full_payment"))
                        {
                            viewmodel.setFullAmount();
                        }
                            break;


                    case PaymentAction.API_ERROR:
                        break;

                        case PaymentAction.CHECKSUM_SUCCESS:
                            //viewmodel.setChecksum();

                            //paytm(paymentAction.getChecksumResponse().getData());
                            String currentAppVersion=getPaytmVersion(PaymentActivity.this);
                            if (versionCompare(currentAppVersion, "8.6.0") < 0) {
                                //Full screen App Invoke flow

                                Intent paytmIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putDouble("nativeSdkForMerchantAmount", 1);
                                bundle.putString("orderid", "202004251336551111");
                               // bundle.putString("txnToken", paymentAction.getChecksumResponse().getData());
                                bundle.putString("txnToken", "DLQQHUXbf+JN0oXswiE6LeLdfRG+b5JUOBT03vAQOe2mEPnvjBze/K86NTaIJ0c53prK39PjkXDSujUycnehrVdhBIJFujRa+S0taI2XIJc=");
                                bundle.putString("mid", "sYwTAG93503854487535");
                                paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRJarvisSplash"));
                                paytmIntent.putExtra("paymentmode", 2); // You must have to pass hard coded 2 here, Else your transaction would not proceed.
                                paytmIntent.putExtra("bill", bundle);
                                startActivityForResult(paytmIntent, 144);
                            }else
                                {
                               // New App Invoke flow

                                Intent paytmIntent = new Intent();
                                paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRRechargePaymentActivity"));
                                paytmIntent.putExtra("paymentmode", 2);
                                paytmIntent.putExtra("enable_paytm_invoke", true);
                                paytmIntent.putExtra("paytm_invoke", true);
                                paytmIntent.putExtra("price", "1"); //this is string amount
                                paytmIntent.putExtra("nativeSdkEnabled", true);
                                paytmIntent.putExtra("orderid", "202004251336551111");
                                //paytmIntent.putExtra("txnToken", paymentAction.getChecksumResponse().getData());
                                    paytmIntent.putExtra("txnToken", "DLQQHUXbf+JN0oXswiE6LeLdfRG+b5JUOBT03vAQOe2mEPnvjBze/K86NTaIJ0c53prK39PjkXDSujUycnehrVdhBIJFujRa+S0taI2XIJc=");

                                    paytmIntent.putExtra("mid", "sYwTAG93503854487535");
                                startActivityForResult(paytmIntent, 144);

                            }
                            break;

                    case PaymentAction.CLICK_PAY:
//                        Intent payIntent=new Intent(PaymentActivity.this,PayUActivity.class);
//                        payIntent.putExtra("amount",viewmodel.anountToPay.get());
//                        payIntent.putExtra("flag",flag);
//                        startActivity(payIntent);

                        Intent intent=new Intent(getApplicationContext(), AxisPaymentActivity.class);
                        intent.putExtra("net_amount",viewmodel.amountToPay.get());
                        intent.putExtra("flag",flag);
                        intent.putExtra("from","pay_online");
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

    private String getPaytmVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo pkgInfo = pm.getPackageInfo(PAYTM_APP_PACKAGE, PackageManager.GET_ACTIVITIES);
            return pkgInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            PaytmUtility.debugLog("Paytm app not installed");
        }
        return null;
    }

    private int versionCompare(String str1, String str2) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            return 1;
        }
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        //set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equalsIgnoreCase(vals2[i])) {
            i++;
        }
        //compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        //the strings are equal or one string is a substring of the other
        //e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 144 && data != null) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.d(TAG, "paytm_key "+key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            Log.d(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.d(TAG, " data response - "+data.getStringExtra("response"));


            //String da=data.getData().toString();
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage"), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra("response"), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaymentType(String payment_type) {
        switch (payment_type) {
            case "interest_payment":
                flag="Receipt";
                payment="Interest Payment";
                binding.layoutAmount.setVisibility(View.GONE);
                binding.layoutInterest.setVisibility(View.VISIBLE);
                binding.tvHeading.setText("INTEREST PAYMENT");
                viewmodel.initLoading(PaymentActivity.this);
                viewmodel.getSettlementDetails("", account_number);

                break;

            case "part_payment":
                flag="Receipt";
                payment="Part Payment";
                binding.tvHeading.setText("PART PAYMENT");
                binding.layoutAmount.setVisibility(View.VISIBLE);
                viewmodel.amountToPay.set("");
                binding.layoutInterest.setVisibility(View.GONE);
                viewmodel.initLoading(PaymentActivity.this);
                viewmodel.getSettlementDetails("", account_number);

                break;

            case "full_payment":
                flag="Closing";
                payment="Full Payment";
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


    public void paytmSdk()
    {
        String host = "https://securegw-stage.paytm.in/";
        String token="11f7bfcb505749a0afb4e4554138fda41601973493706";
        String orderId="DIGIPATM201006140805308187474";
        String mid="uNtRLU10651008408055";
        String callbackUrl="https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        int amount=10;
        PaytmOrder paytmOrder = new PaytmOrder(orderId, mid, token, String.valueOf(amount), callbackUrl);

        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                String s=bundle.toString();
            }

            @Override
            public void networkNotAvailable() {

                Toast.makeText(PaymentActivity.this, "no network", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorProceed(String s) {
                String s1=s;
                Toast.makeText(PaymentActivity.this, s1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Toast.makeText(PaymentActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Toast.makeText(PaymentActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Toast.makeText(PaymentActivity.this, s+s1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Toast.makeText(PaymentActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this,144);

    }
    public void payTm()
    {

        String token="73b70ad0556946b69fa1699cf474c0a31601964767418";
        String orderId="DIGIPATM201006114238357905773";
        String mid="uNtRLU10651008408055";
        String currentAppVersion=getPaytmVersion(PaymentActivity.this);
        if (versionCompare(currentAppVersion, "8.6.0") < 0) {
            //Full screen App Invoke flow
            paytmIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putDouble("nativeSdkForMerchantAmount", 10);
            bundle.putString("orderid", orderId);
            bundle.putString("txnToken", token);
            bundle.putString("mid", mid);
            paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRJarvisSplash"));
            paytmIntent.putExtra("paymentmode", 2); // You must have to pass hard coded 2 here, Else your transaction would not proceed.
            paytmIntent.putExtra("bill", bundle);
            startActivityForResult(paytmIntent, 144);

        }else
        {
            // New App Invoke flow


            paytmIntent = new Intent();
            paytmIntent.setComponent(new ComponentName("net.one97.paytm", "net.one97.paytm.AJRRechargePaymentActivity"));
            paytmIntent.putExtra("paymentmode", 2);
            paytmIntent.putExtra("enable_paytm_invoke", true);
            paytmIntent.putExtra("paytm_invoke", true);
            paytmIntent.putExtra("price", "10"); //this is string amount
            paytmIntent.putExtra("nativeSdkEnabled", true);
            paytmIntent.putExtra("orderid", orderId);
            paytmIntent.putExtra("txnToken", token);
            paytmIntent.putExtra("mid", mid);
            startActivityForResult(paytmIntent, 144);

        }
    }


    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_DLmmkTrql5zJbY");
        //
//        co.setImage(R.drawable.nfc_toolbar_logo);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Riviresa Nidhi Limited");
            options.put("description", payment);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Double.parseDouble(viewmodel.amountToPay.get())*100));

            JSONObject preFill = new JSONObject();
            JSONObject theme = new JSONObject();
            options.put("prefill.email", "dddd@leslin.com");
            options.put("prefill.contact", "8714155345");
            theme.put("color", "#c91854");

           // options.put("prefill", preFill);
            options.put("theme", theme);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(getApplicationContext(), RenewLoanActivity.class);
        intent.putExtra("net_amount",viewmodel.amountToPay.get());
        intent.putExtra("flag",flag);
        intent.putExtra("from","pay_online");
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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