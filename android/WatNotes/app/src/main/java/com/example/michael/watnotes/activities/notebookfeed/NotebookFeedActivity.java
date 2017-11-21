package com.example.michael.watnotes.activities.notebookfeed;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.DrawerActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedActivity extends DrawerActivity {

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
}
