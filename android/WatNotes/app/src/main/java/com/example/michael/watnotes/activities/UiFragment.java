package com.example.michael.watnotes.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by michael on 10/1/17.
 */

public abstract class UiFragment extends WatNotesFragment {

    protected abstract int getLayoutId();
    protected abstract void initializeUi(View view);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        initializeUi(rootView);
        return rootView;
    }

}
