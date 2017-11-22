package com.example.michael.watnotes.activities.notebookfeed;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.DrawerActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedActivity extends DrawerActivity {

    public static final String USER_ID_KEY = "USER_ID_KEY";
    public static final int NO_USER_ID = -1;

    @Override
    protected UiFragment createUiFragment() {
        return new NotebookFeedUiFragment();
    }

    @Override
    protected ServiceFragment createServiceFragment() {
        return new NotebookFeedServiceFragment();
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.notebooks);
    }

    public Integer getUserId() {
        return getIntent().getIntExtra(USER_ID_KEY, NO_USER_ID);
    }
}
