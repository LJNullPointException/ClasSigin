package com.design.classsign.common.retrofit;

/**
 * Created by yuer on 2016/10/27.
 */
public interface ResponseInterface {

    //请求开始
    void onStart(String requestTag);
    //请求成功
    void onSuccess(Object response, String requestTag);
    //请求错误
    void onError(Throwable e, String requestTag);
     //请求结束
    void onCompleted(String requestTag);

}
