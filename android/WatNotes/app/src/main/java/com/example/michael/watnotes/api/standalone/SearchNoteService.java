package com.example.michael.watnotes.api.standalone;

import android.util.Log;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.SingleApiService;
import com.example.michael.watnotes.util.IOUtil;

import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public class SearchNoteService extends SingleApiService {

    private DefaultCompletionCallback mCompletionCallback;
    private DefaultFailureCallback mFailureCallback;

    public void requestService(String query,
                               DefaultCompletionCallback completionCallback,
                               DefaultFailureCallback failureCallback) {
        ApiRequest request = new ApiRequest("search/", ApiRequest.RequestType.GET, this);
        request.addParam("q", query);
        request.startRequest();
    }

    @Override
    public void onRequestFailure() {
        Log.d("RequestFailure", "RequestFailure");
        if (mFailureCallback != null) {
            mFailureCallback.onFailure();
        }
    }

    @Override
    public void onRequestSuccess(JSONObject requestResponse) {
        Log.d("RequestSuccess", requestResponse.toString());
        if (mCompletionCallback != null) {
            mCompletionCallback.onSuccess();
        }
    }
}
