package com.example.michael.watnotes.api.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public class Notebook extends BaseModel {

    private int mId;
    private Course mCourse;
    private User mUser;

    public Notebook(JSONObject json) {
        super(json);
    }

    @Override
    public void parseJSON(JSONObject json) throws JSONException {
        mId = json.getInt("id");
        mCourse = new Course(json.getJSONObject("course"));
        mUser = new User(json.getJSONObject("user"));
    }

    public Course getCourse() {
        return mCourse;
    }

    public User getUser() {
        return mUser;
    }
}
