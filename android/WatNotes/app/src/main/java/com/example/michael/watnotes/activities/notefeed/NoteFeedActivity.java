package com.example.michael.watnotes.activities.notefeed;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.DrawerActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class NoteFeedActivity extends DrawerActivity {

    public static final String QUERY_KEY = "QUERY_KEY";
    public static final String NOTEBOOK_ID_KEY = "NOTEBOOK_ID_KEY";
    private static final int NO_NOTEBOOK_ID = -1;

    @Override
    protected UiFragment createUiFragment() {
        return new NoteFeedUiFragment();
    }

    @Override
    protected ServiceFragment createServiceFragment() {
        return new NoteFeedServiceFragment();
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.notes);
    }

    public String getQuery() {
        return getIntent().getStringExtra(QUERY_KEY);
    }

    public int getNotebookId() {
        return getIntent().getIntExtra(NOTEBOOK_ID_KEY, NO_NOTEBOOK_ID);
    }
}
