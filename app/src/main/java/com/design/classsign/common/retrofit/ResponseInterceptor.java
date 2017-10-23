package com.design.classsign.common.retrofit;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * Created by yuer on 2016/10/27.
 */
public class ResponseInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string().trim();
        String re = new StringBuffer(content).toString();
//        String key = SPutils.getString(Constant.RECEIPT); // 128 bit key
//        if (!request.url().toString().contains(RequestPath.deviceIdentification) && !request.url().toString().contains(RequestPath.getOSSToken) && !request.url().toString().contains(RequestPath.postStatistics) && response.code() == 200) {
//            content = AESUtils.decrypt(key, Constant.INIT_VECTOR, content);
//        }
//
//        if (content == null) {
//            content = re;
//        }
//        try {
//            String body = content;
//            JSONObject json = new JSONObject(body);
//            if (json.has("error")) {
//                StatisticsUtil.processLog(StatisticsUtil.NET_ERROT, "response body:" + content + "**url:" + request.url().toString());
//                JSONObject errJson = json.getJSONObject("error");
//                if (Integer.parseInt(errJson.get("code").toString()) == 1001) { // 票据错误
//                    HttpMethods.getInstance().login(new BaseRequestBody(null)).subscribe(new MyObserver<ResponseGuidePage.ResultBean.DataBean>() {
//                        @Override
//                        protected void onError(ApiException ex) {
//                        }
//
//                        @Override
//                        public void onNext(ResponseGuidePage.ResultBean.DataBean dataBean) {
//                            super.onNext(dataBean);
//                            SPutils.put(Constant.WXOPEN,dataBean.getWxopen());
//                            SPutils.put(Constant.RECEIPT, dataBean.getReceipt());
//                            SPutils.put(Constant.SERIAL_NUMBER, dataBean.getSerial_number());
//                            SPutils.put(Constant.ONLINE_HOST, dataBean.getOnline_host());
//                            SPutils.put(Constant.IMG_HOST, dataBean.getImg_host());
//                            SPutils.put(Constant.STORE_ID, dataBean.getStore_id());
//                            SPutils.put(Constant.STORE_PID, dataBean.getStore_pid());
//                        }
//                    });
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        LogUtil.e(RetrofitNewInstance.NET_WORK_TAG, "response body:" + content + "**url:" + request.url().toString());
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
