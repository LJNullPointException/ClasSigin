package com.design.classsign.common;

import com.design.classsign.bese.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yzm on 2017/10/23.
 */

public class RetrofitUtils {
    public static Retrofit mRetrofit;

    public Retrofit getInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constant.SERVER_IP)
                        .build();
        }
        return mRetrofit;

    }


}
