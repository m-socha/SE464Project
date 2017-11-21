package com.example.michael.watnotes.activities.search;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.DrawerActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class SearchActivity extends DrawerActivity {

    @Override
    protected UiFragment createUiFragment() {
        return new SearchUiFragment();
    }

    @Override
    protected ServiceFragment createServiceFragment() {
        return new SearchServiceFragment();
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.search);
    }
}
