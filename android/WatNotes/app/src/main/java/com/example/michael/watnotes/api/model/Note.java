package com.example.michael.watnotes.api.model;

import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11/21/17.
 */

public class Note extends BaseModel {

    private int mId;
    private int mNotebookId;
    private String mData;
    private String mExtension;
    private String mFormat;
    private double mIndex;

    public Note(JSONObject json) {
        super(json);
    }

    @Override
    public void parseJSON(JSONObject json) throws JSONException {
        mId = json.getInt("id");
        mNotebookId = json.getInt("notebook_id");
        mData = json.optString("data");
        mExtension = json.getString("extension");
        mFormat = json.getString("format");
        mIndex = json.getDouble("index");
    }

    public int getId() {
        return mId;
    }

    public String getData() {
        return mData;
    }

    public String getExtension() {
        return mExtension;
    }

    public String getFormat() {
        return mFormat;
    }

    public double getIndex() {
        return mIndex;
    }
}