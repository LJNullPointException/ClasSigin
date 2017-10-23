package com.design.classsign.common.retrofit;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by yuer on 2016/10/27.
 */
public class RequestInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        long time = System.currentTimeMillis();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = null;
        authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("", "");
        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .header("accept", "application/json")
                .header("Content-type", "application/json")
                .build();
        return chain.proceed(newRequest);
    }

    private String getRequestBody(RequestBody requestBody) throws IOException {
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                return buffer.readString(charset);
            }
        }
        return "not contains human readable text";
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlaintext(Buffer buffer) throws IOException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
