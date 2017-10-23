package com.design.classsign.common.retrofit;


import android.text.format.Time;
import android.util.TimeUtils;

import com.design.classsign.bese.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yuer on 2016/10/27.
 */
public class RetrofitNewInstance {

    public static String NET_WORK_TAG = "yzmnetwork-----";

    private static Retrofit mRetrofit;
    private static Converter.Factory gonConverterFactory = ScalarsConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ResponseInterceptor responseInterceptor = new ResponseInterceptor();
    private static RequestInterceptor requestInterceptor = new RequestInterceptor();


    private static OkHttpClient mOkHttpClient;

    public static Retrofit getInstance() {
        if (mRetrofit != null) {
            return mRetrofit;
        }
        if (mOkHttpClient == null) {
            mOkHttpClient =
                    new OkHttpClient().newBuilder()
                            .connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS) //设置链接超时时间
                            .readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(requestInterceptor)
                            .addInterceptor(responseInterceptor)
                            .build();
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVER_IP)
                .client(mOkHttpClient)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gonConverterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

}
