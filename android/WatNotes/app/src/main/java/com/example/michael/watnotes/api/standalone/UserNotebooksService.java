package com.example.michael.watnotes.api.standalone;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.ApiService;
import com.example.michael.watnotes.api.core.SingleApiService;
import com.example.michael.watnotes.api.model.Notebook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class UserNotebooksService extends SingleApiService {

    public interface UserNotebooksCompletionCallback {
        void onSuccess(List<Notebook> notebooks);
    }

    private UserNotebooksCompletionCallback mCompletionCallback;
    private ApiService.DefaultFailureCallback mFailureCallback;

    public void requestService(int userId,
                               UserNotebooksCompletionCallback completionCallback,
                               ApiService.DefaultFailureCallback failureCallback) {
        mCompletionCallback = completionCallback;
        mFailureCallback = failureCallback;

        ApiRequest request = new ApiRequest("users/" + userId + "/notebooks", ApiRequest.RequestType.GET, this);
        request.addParam("load", "course,user");
        request.startRequest();
    }

    @Override
    public void onRequestFailure() {
        if (mFailureCallback != null) {
            mFailureCallback.onFailure();
        }
    }

    @Override
    public void onRequestSuccess(JSONObject requestResponse) throws JSONException {
        if (mCompletionCallback != null) {
            JSONArray notebookJsonArray = requestResponse.getJSONArray("items");
            List<Notebook> notebooks = new ArrayList<>();
            for (int i = 0; i < notebookJsonArray.length(); i++) {
                Notebook notebook = new Notebook(notebookJsonArray.getJSONObject(i));
                notebooks.add(notebook);
            }
            mCompletionCallback.onSuccess(notebooks);
        }
    }
}
