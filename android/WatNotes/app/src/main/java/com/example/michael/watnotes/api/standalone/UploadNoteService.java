package com.example.michael.watnotes.api.standalone;

import android.util.Log;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.SingleApiService;
import com.example.michael.watnotes.util.IOUtil;

import org.json.JSONObject;

/**
 * Created by michael on 10/18/17.
 */

public class UploadNoteService extends SingleApiService {

    private DefaultCompletionCallback mCompletionCallback;
    private DefaultFailureCallback mFailureCallback;

    public void requestService(int notebookId, IOUtil.FileInfo fileInfo, String fileFormat,
                               DefaultCompletionCallback completionCallback,
                               DefaultFailureCallback failureCallback) {
        ApiRequest request = new ApiRequest("notebooks/" + notebookId + "/notes?form=1", this);
        request.addFormFile("data", fileInfo);
        request.addParam("format", fileFormat);
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
