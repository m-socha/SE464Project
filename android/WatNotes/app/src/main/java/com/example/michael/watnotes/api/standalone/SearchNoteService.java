package com.example.michael.watnotes.api.standalone;

import android.util.Log;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.SingleApiService;
import com.example.michael.watnotes.api.model.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class SearchNoteService extends SingleApiService {

    public interface SearchNoteCompletionCallback {
        void onSuccess(List<Note> notes);
    }

    private SearchNoteCompletionCallback mCompletionCallback;
    private DefaultFailureCallback mFailureCallback;

    public void requestService(String query,
                               SearchNoteCompletionCallback completionCallback,
                               DefaultFailureCallback failureCallback) {
        mCompletionCallback = completionCallback;
        mFailureCallback = failureCallback;

        ApiRequest request = new ApiRequest("search", ApiRequest.RequestType.GET, this);
        request.addParam("q", query);
        request.addParam("in", "notes");
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
            JSONArray noteJsonArray = requestResponse.getJSONObject("items").getJSONArray("notes");
            List<Note> notes = new ArrayList<>();
            for (int i = 0; i < noteJsonArray.length(); i++) {
                Note note = new Note(noteJsonArray.getJSONObject(i));
                notes.add(note);
            }
            mCompletionCallback.onSuccess(notes);
        }
    }
}
