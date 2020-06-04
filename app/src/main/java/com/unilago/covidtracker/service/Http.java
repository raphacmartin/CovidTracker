package com.unilago.covidtracker.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http {
    private static final String API_URL = "https://covid19-brazil-api.now.sh/api/report/v1/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void get(final String url, final HttpCallback<JSONObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(API_URL + url)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();

                    JSONObject res = new JSONObject(Objects.requireNonNull(response.body()).string());
                    callback.onComplete(res);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }).start();

    }

    public interface HttpCallback<T>  {
        void onComplete(T res);
        void onError(Exception e);
    }
}
