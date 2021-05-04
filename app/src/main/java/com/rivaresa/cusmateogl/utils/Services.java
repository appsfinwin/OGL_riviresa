package com.rivaresa.cusmateogl.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.meetic.marypopup.MaryPopup;
import com.rivaresa.cusmateogl.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Services {

    public static MaryPopup ErrorDialoge(final Context context, String error) {
        MaryPopup maryPopup;
        maryPopup = MaryPopup.with((Activity) context)
                .cancellable(false)
                .draggable(true)
                .scaleDownDragging(true)
                .fadeOutDragging(true)
                .center(true)
                .blackOverlayColor(Color.parseColor("#DD444444"))
                .backgroundColor(Color.parseColor("#EFF4F5"));


        return maryPopup;
    }

    public static ProgressDialog showProgressDialog(Context context) {

        ProgressDialog warningDialog = new ProgressDialog(context);
        // warningDialog.setContentView(R.layout.layout_progress_dialog);


        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setTitle("Loading");
        warningDialog.setMessage("Please wait...");
        warningDialog.setCancelable(false);
        warningDialog.show();
        return warningDialog;
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


}
