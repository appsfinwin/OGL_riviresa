package com.riviresa.custmate.ogl.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.riviresa.custmate.R;

import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class Services {


    public static Dialog showProgressDialog(Context context) {

//        ProgressDialog warningDialog = new ProgressDialog(context);
//        //warningDialog.setContentView(R.layout.layout_progress_dialog);
//        warningDialog.setCanceledOnTouchOutside(false);
//        warningDialog.setTitle("Loading");
//        warningDialog.setMessage("Please wait...");
//        warningDialog.setCancelable(false);
//        warningDialog.show();

        Dialog warningDialog = new Dialog(context);
        warningDialog.setContentView(R.layout.layout_progress_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setCancelable(false);
        warningDialog.show();
        return warningDialog;
    }

    public static Dialog errorDialog(Context context,String error) {

        Dialog errorDialog = new Dialog(context);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        errorDialog.getWindow().setElevation(0);
        //errorDialog.getWindow().setLayout((int) WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT);
        @SuppressLint("InflateParams")
        View customView_ = LayoutInflater.from(context).inflate(R.layout.layout_error_popup, null);
        TextView tv_error_ = customView_.findViewById(R.id.tv_error);
        TextView tvOkey = customView_.findViewById(R.id.tv_error_ok);
        tv_error_.setText(error);

        tvOkey.setOnClickListener(v -> {
            errorDialog.cancel();
        });


       // errorDialog.addContentView(customView_,new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,  WindowManager.LayoutParams.WRAP_CONTENT));
        errorDialog.setContentView(customView_);
        errorDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        errorDialog.setCanceledOnTouchOutside(false);
        errorDialog.setCancelable(false);
        errorDialog.show();
        return errorDialog;
    }

    public static Dialog showLoading(Context context) {

        Dialog warningDialog = new Dialog(context);
        warningDialog.setContentView(R.layout.layout_progress_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setCancelable(false);
        warningDialog.show();
        return warningDialog;
    }


    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static String getRandomNumber(){
        Random random = new Random();
        @SuppressLint("DefaultLocale") String id = String.format("%04d", random.nextInt(10000));
        return id;
    }

    public static String getCurrentTime()
    {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));

        int currentHour = cal.get(Calendar.HOUR);
        int currentMinutes = cal.get(Calendar.MINUTE);
        int currentSeconds = cal.get(Calendar.SECOND);

        return  currentHour+":"+currentMinutes;
    }

    public static boolean checktimings(String currentTime, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(currentTime);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }



}
