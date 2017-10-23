package com.design.classsign.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by yzm on 2017/4/5.
 */

public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(ResponseBody value) throws IOException {
        try {

            String body = value.string();
            JSONObject json = new JSONObject(body);

            if (json.has("result")) {

                body = json.get("result").toString();
                return adapter.fromJson(body);

            } else if (json.has("error")) {

                body = json.get("error").toString();
                return adapter.fromJson(body);
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }

        return null;
    }
}
