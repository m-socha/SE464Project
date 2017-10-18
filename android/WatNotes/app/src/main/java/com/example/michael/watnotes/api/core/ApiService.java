package com.example.michael.watnotes.api.core;

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
    protected abstract String endPoint();

}
