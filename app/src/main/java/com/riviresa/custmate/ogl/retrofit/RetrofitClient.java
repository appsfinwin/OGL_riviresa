package com.riviresa.custmate.ogl.retrofit;

import com.riviresa.custmate.ogl.supporting_class.ConstantClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit_login = null;
    private static Retrofit retrofitAxis = null;
    private static Retrofit retrofitRazorPay = null;
    private static ApiInterface myApi = null;
    private static ApiInterface axisApi = null;
    private static ApiInterface razorPayApi = null;

//    public static Retrofit RetrofitClient() {
//
//        if (retrofit_login == null) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//
//            final OkHttpClient client = new OkHttpClient.Builder()
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .addInterceptor(logging)
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .build();
//
//            retrofit_login = new Retrofit.Builder()
//
//                    //.baseUrl("http://192.168.0.221:170/")
//                    //.baseUrl("http://oglsales.digicob.in/")//sales
//                    //.baseUrl("http://testriviresaogl.digicob.in/")//test rivaresa
//
//                    //.baseUrl("http://oglriviresalive.digicob.in:2524/")//rivaresa
//                    //.baseUrl("http://ogllocaltest.digicob.in/")//kosamattom test
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//        }
//        return retrofit_login;
//    }

    public  static ApiInterface getApi(){
        if (myApi==null)
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            final OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            myApi = new Retrofit.Builder()

                    //.baseUrl("http://192.168.0.221:170/")
                    //.baseUrl("http://oglsales.digicob.in/")//sales
                    //.baseUrl("http://testriviresaogl.digicob.in/")//test rivaresa
                    .baseUrl("http://oglriviresalive.digicob.in:2524/")//rivaresa live
                    //.baseUrl("http://oglsales.digicob.in/")//kosamattom test
                    //.baseUrl("http://ogllocaltest.digicob.in/")//kosamattom test
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiInterface.class);

        }
        return myApi;
    }

    public static ApiInterface getAxisApi() {

        if (axisApi == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            final OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .build();

            axisApi = new Retrofit.Builder()
                    //.baseUrl("https://ogluvnl.digicob.in/")
                    .baseUrl(ConstantClass.AXIS_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiInterface.class);
        }
        return axisApi;
    }

    public static ApiInterface getRazorPayApi() {

        if (razorPayApi == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            final OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .build();

            razorPayApi = new Retrofit.Builder()
                    //.baseUrl("https://ogluvnl.digicob.in/")
                    .baseUrl(ConstantClass.RAZORPAY_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiInterface.class);
        }
        return razorPayApi;
    }
}