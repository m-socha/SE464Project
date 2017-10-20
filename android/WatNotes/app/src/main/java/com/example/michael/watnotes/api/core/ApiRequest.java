package com.example.michael.watnotes.api.core;

import android.content.ContentResolver;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Callback;
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

    private static final String BASE_URL = "http://watnotes.herokuapp.com/";

    private boolean mRequestCancelled = false;
    private String mEndpoint;

    // TODO: Make this a proper param map
    private String mFileKey;
    private String mFileUri;
    private String mFileMimeType;
    private byte[] mFileContents;

    public ApiRequest(String endpoint) {
        mEndpoint = endpoint;
    }

    public void addFormFile(String key, String uri, String mimeType, byte[] fileContents) {
        mFileKey = key;
        mFileUri = uri;
        mFileMimeType = mimeType;
        mFileContents = fileContents;
    }

    public void startRequest() {
        OkHttpClient httpClient = new OkHttpClient();

        Log.d("FileContentsLength", mFileContents.length + "");
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("data", mFileUri, RequestBody.create(MediaType.parse(mFileUri), mFileContents))
                .addFormDataPart("index", "0")
                .addFormDataPart("format", mFileMimeType)
                .build();

        final Request request = new Request.Builder()
                .url(getCompleteEndpoint())
                .post(requestBody)
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
                    Log.d("ApiResponse1", request.url().toString());
                    Log.d("ApiResponse2", response.code() + "");
                    Log.d("ApiResponse3", response.message());
                    Log.d("ApiResponse4", request.method());
                    if (response.isSuccessful()) {

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
