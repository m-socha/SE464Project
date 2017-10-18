package com.example.michael.watnotes.api.core;



import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by michael on 10/17/17.
 */

public class ApiRequest {

    private static final String BASE_URL = "http://watnotes.herokuapp.com/";

    private boolean mRequestCancelled = false;
    private String mEndpoint;

    public ApiRequest(String endpoint) {
        mEndpoint = endpoint;
    }

    public void startRequest() {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getCompleteEndpoint())
                .build();

        mRequestCancelled = false;
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!mRequestCancelled) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!mRequestCancelled) {
                    Log.d("ApiResponse", response.message());
                }
            }
        });
    }

    public void cancelRequest() {
        mRequestCancelled = true;
    }

    private String getCompleteEndpoint() {
        return BASE_URL + mEndpoint;
    }
}
