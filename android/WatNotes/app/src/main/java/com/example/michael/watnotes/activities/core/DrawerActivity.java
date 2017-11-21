package com.example.michael.watnotes.activities.core;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 10/1/17.
 */

public abstract class DrawerActivity extends BaseActivity {

    private DrawerLayout mNavDrawer;
    private ListView mNavDrawerListView;

    protected abstract String getActionBarTitle();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggleNavDrawer();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.drawer_activity;
    }

    @Override
    protected void setupUi() {
        mNavDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerListView = (ListView) findViewById(R.id.drawer_layout_list_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getActionBarTitle());
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mNavDrawer, R.string.open_drawer, R.string.close_drawer);
        mNavDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        List<String> drawerListItems = new ArrayList();
        drawerListItems.add(getString(R.string.search));
        mNavDrawerListView.setAdapter(new DrawerListAdapter(this, drawerListItems));
    }

    @Override
    protected int getUiFragmentContainerView() {
        return R.id.drawer_content_view;
    }

    private void toggleNavDrawer() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            openNavDrawer();
        }
    }

    private boolean isNavDrawerOpen() {
        return mNavDrawer.isDrawerOpen(Gravity.LEFT);
    }

    private void openNavDrawer() {
        mNavDrawer.openDrawer(Gravity.LEFT);
    }

    private void closeNavDrawer() {
        mNavDrawer.closeDrawer(Gravity.LEFT);
    }
}
