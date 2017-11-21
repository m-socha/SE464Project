package com.example.michael.watnotes.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public class User extends BaseModel {

    private int mId;
    private String mEmail;
    private String mName;

    public User(JSONObject json) {
        super(json);
    }

    @Override
    public void parseJSON(JSONObject json) throws JSONException {
        mId = json.getInt("id");
        mEmail = json.getString("email");
        mName = json.getString("name");
    }

    public String getEmail() {
        return mEmail;
    }

    public String getName() {
        return mName;
    }
}
