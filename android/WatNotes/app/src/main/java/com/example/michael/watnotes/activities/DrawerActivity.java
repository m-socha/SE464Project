package com.example.michael.watnotes.activities;

import com.example.michael.watnotes.R;

/**
 * Created by michael on 10/1/17.
 */

public abstract class DrawerActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.drawer_activity;
    }

    protected int getUiFragmentContainerView() {
        return R.id.drawer_content_view;
    }

}
