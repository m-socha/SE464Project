package com.example.michael.watnotes.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public class Course extends BaseModel {

    private int mId;
    private String mCode;
    private String mTitle;

    public Course(JSONObject json) {
        super(json);
    }

    @Override
    public void parseJSON(JSONObject json) throws JSONException {
        mId = json.getInt("id");
        mCode = json.getString("code");
        mTitle = json.getString("title");
    }

    public String getCode() {
        return mCode;
    }

    public String getTitle() {
        return mTitle;
    }
}
