package com.example.michael.watnotes.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public abstract class BaseModel {

    public BaseModel(JSONObject json) {
        try {
            parseJSON(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected abstract void parseJSON(JSONObject json) throws JSONException;
}
