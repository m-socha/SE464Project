package com.example.michael.watnotes.api.core;

import org.json.JSONObject;

/**
 * Created by michael on 10/17/17.
 */

public abstract class ApiService {

    public interface DefaultCompletionCallback {
        void onSuccess();
    }

    public interface DefaultFailureCallback {
        void onFailure();
    }

    protected abstract void cancelAllRequests();

    public abstract void onRequestFailure();
    public abstract void onRequestSuccess(JSONObject requestResponse);

}
