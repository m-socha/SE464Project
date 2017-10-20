package com.example.michael.watnotes.api.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 10/17/17.
 */

public abstract class MultiApiService<T> extends ApiService {

    Map<T, ApiRequest> mActiveRequests = new HashMap<>();

    public void startRequest(ApiRequest request, T requestId) {
        cancelRequest(requestId);

        mActiveRequests.put(requestId, request);
        request.startRequest();
    }

    private void cancelRequest(T requestId) {
        if (mActiveRequests.get(requestId) != null) {
            mActiveRequests.remove(requestId);
        }
    }

    @Override
    public void cancelAllRequests() {
        mActiveRequests.clear();
    }

}
