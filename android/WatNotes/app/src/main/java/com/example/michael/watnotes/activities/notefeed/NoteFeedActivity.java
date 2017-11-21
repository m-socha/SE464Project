package com.example.michael.watnotes.activities.notefeed;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.DrawerActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class NoteFeedActivity extends DrawerActivity {

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
}
