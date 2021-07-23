package com.rivaresa.cusmateogl.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityLoginBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.login.action.LoginAction;
import com.rivaresa.cusmateogl.reset_password.forgot_password.ForgotPasswordActivity;
import com.rivaresa.cusmateogl.signup.SignupActivity;
import com.rivaresa.cusmateogl.supporting_class.ConstantClass;
import com.rivaresa.cusmateogl.utils.DataHolder;
import com.rivaresa.cusmateogl.utils.VersionChecker;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends BaseActivity {

    LoginViewmodel viewmodel;
    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String latestVersion="",currentVersion="";
    InstallStateUpdatedListener installStateUpdatedListener;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.bg_yellow));

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = new ViewModelProvider(this).get(LoginViewmodel.class);
        viewmodel.setActivity(this);
        binding.setViewmodel(viewmodel);
        if (isNetworkOnline()) {
           // checkVersion();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

//        String address = Services.getMacAddr();
////        Toast.makeText(this, address, Toast.LENGTH_SHORT).show();

        viewmodel.getLoginLivedata().observe(this, new Observer<LoginAction>() {
            @Override
            public void onChanged(LoginAction loginAction) {

                viewmodel.cancelLoading();
                switch (loginAction.getAction()) {
                    case LoginAction.LOGIN_SUCCESS:

                        if (loginAction.getLoginResponse().getBankDetails().size() > 0) {
                            //DataHolder.getInstance().bankDetails = (loginAction.getLoginResponse().getBankDetails());
                            //DataHolder.getInstance().selectedBankData = loginAction.getLoginResponse().getBankDetails().get(0);
                        }

                        DataHolder.getInstance().loginData = (loginAction.getLoginResponse().getLoginData());


                        editor.putString("name", loginAction.loginResponse.getLoginData().getName());
                        editor.putString("email", loginAction.loginResponse.getLoginData().getEmailId());
                        editor.putString("phone", loginAction.loginResponse.getLoginData().getPhoneNum());
                        editor.putString("cust_id", loginAction.loginResponse.getLoginData().getCustId());
                        editor.putBoolean(ConstantClass.IS_LOGIN,false);
                        editor.commit();

                        viewmodel.initLoading(LoginActivity.this);
                        viewmodel.generateOtp(loginAction.loginResponse.getLoginData().getPhoneNum());


                        break;

                    case LoginAction.OTP_SUCCESS:

                        viewmodel.cancelLoading();
//                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
//                        intent.putExtra("from","login");
//                        intent.putExtra("otp_id",loginAction.otpCreationResponse.getOtp().getOtpId());
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        finish();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();

                        break;
                    case LoginAction.API_ERROR:
                        viewmodel.ErrorDialoge(loginAction.getError());
                        break;
                    case LoginAction.CLICK_FORGOT_PASSWORD:

                        Intent forgotIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                        //Intent forgotIntent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
                        startActivity(forgotIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                        View customView= LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_error_layout,null);
//                        TextView tv_error=customView.findViewById(R.id.tv_error);
//                        tv_error.setText("Customer executive will contact you soon");
//                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Request has been made!")
//                                .setCustomView(customView)
//                                .show();
                        break;

                    case LoginAction.CLICK_SIGN_UP:

                        Intent signuptIntent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(signuptIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;


                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.mAction.setValue(new LoginAction(LoginAction.DEFAULT));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
    }

    private void checkVersion() {

        VersionChecker versionChecker = new VersionChecker();
        try {

            latestVersion = versionChecker.execute().get();
            // Toast.makeText(getActivity().getApplicationContext(), latestVersion , Toast.LENGTH_SHORT).show();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert info != null;
        currentVersion = info.versionName;
        if (latestVersion==null)
        {
            viewmodel.cancelLoading();
            //Toast.makeText(getActivity().getApplicationContext(), "Slow network Detected!", Toast.LENGTH_SHORT).show();
        }else {
            viewmodel.cancelLoading();
            if (Float.parseFloat(currentVersion) < Float.parseFloat(latestVersion)) {
                showUpdateDialog();
            }
        }
    }


    private void showUpdateDialog() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this, R.style.alertDialog);
        // Setting Dialog Title
        alertDialog2.setTitle("Update Available");
        // Setting Dialog Message
        alertDialog2.setMessage("There is a newer version of this application is available");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.rivaresa.cusmateogl"));
                        startActivity(i);
                    }
                });
        alertDialog2.setCancelable(false);
        alertDialog2.show();

    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

    @Override
    protected void onStart() {
        super.onStart();

        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED){
                if (mAppUpdateManager != null){
                    if (installStateUpdatedListener!=null) {
                        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                    }
                }

            } else {
                //Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
            }
        };


        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, LoginActivity.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else {
                //Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });
    }

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.relativeLayout),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null){
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.red_btn_bg_color));
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }
}