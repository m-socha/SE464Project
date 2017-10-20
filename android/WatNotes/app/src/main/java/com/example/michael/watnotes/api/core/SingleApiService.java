package com.example.michael.watnotes.api.core;

/**
 * Created by michael on 10/17/17.
 */

public abstract class SingleApiService extends ApiService {

    ApiRequest mRequest;

    public void startRequest(ApiRequest request) {
        cancelRequest();

        mRequest = request;
        mRequest.startRequest();
    }

    private void cancelRequest() {
        if (mRequest != null) {
            mRequest.cancelRequest();
        }
    }

    @Override
    public void cancelAllRequests() {
        cancelRequest();
    }

}
