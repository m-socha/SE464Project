package com.example.michael.watnotes.api.core;

import android.util.Log;

import com.example.michael.watnotes.util.IOUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by michael on 10/17/17.
 */

public class ApiRequest {

    public enum RequestType {
        GET, POST
    }

    private static final String BASE_URL = "http://watnotes.herokuapp.com/";

    private boolean mRequestCancelled = false;
    private String mEndpoint;
    private RequestType mRequestType;
    private ApiService mApiService;

    private Map<String, String> mParamMap = new HashMap();
    private Map<String, IOUtil.FileInfo> mFileMap = new HashMap();

    public ApiRequest(String endpoint, RequestType requestType, ApiService apiService) {
        mEndpoint = endpoint;
        mRequestType = requestType;
        mApiService = apiService;
    }

    public void addParam(String key, int value) {
        mParamMap.put(key, String.valueOf(value));
    }

    public void addParam(String key, float value) {
        mParamMap.put(key, String.valueOf(value));
    }

    public void addParam(String key, String value) {
        mParamMap.put(key, value);
    }

    public void addFormFile(String key, IOUtil.FileInfo fileInfo) {
        mFileMap.put(key, fileInfo);
    }

    public void startRequest() {
        OkHttpClient httpClient = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(getCompleteEndpoint()).newBuilder();

        if (mRequestType == RequestType.POST) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            for (Map.Entry<String, String> param : mParamMap.entrySet()) {
                requestBodyBuilder.addFormDataPart(param.getKey(), param.getValue());
            }

            for (Map.Entry<String, IOUtil.FileInfo> param : mFileMap.entrySet()) {
                IOUtil.FileInfo fileInfo = param.getValue();
                requestBodyBuilder.addFormDataPart(param.getKey(), fileInfo.getFileUri(), RequestBody.create(MediaType.parse(fileInfo.getFileUri()), fileInfo.getFileContents()));
            }

            requestBuilder.post(requestBodyBuilder.build());
        } else if (mRequestType == RequestType.GET) {
            for (Map.Entry<String, String> param : mParamMap.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request request = requestBuilder
                .url(urlBuilder.build())
                .build();

        mRequestCancelled = false;
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!mRequestCancelled) {
                    e.printStackTrace();
                    mApiService.onRequestFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!mRequestCancelled) {
                    if (response.isSuccessful()) {
                        try {
                            String responseString = response.body().string().toString();
                            JSONObject responseJson = new JSONObject(responseString);
                            mApiService.onRequestSuccess(responseJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mApiService.onRequestFailure();
                        }
                    } else {
                        mApiService.onRequestFailure();
                    }
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
