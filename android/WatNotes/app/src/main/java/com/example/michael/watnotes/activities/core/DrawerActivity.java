package com.example.michael.watnotes.activities.core;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;
import com.example.michael.watnotes.activities.notebookfeed.NotebookFeedActivity;
import com.example.michael.watnotes.activities.search.SearchActivity;
import com.example.michael.watnotes.activities.uploadnotes.UploadNotesActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 10/1/17.
 */

public abstract class DrawerActivity extends BaseActivity {

    private static final int UPLOAD_INDEX = 0;
    private static final int NOTEBOOKS_INDEX = 1;
    private static final int SEARCH_INDEX = 2;

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
        drawerListItems.add(getString(R.string.drawer_upload));
        drawerListItems.add(getString(R.string.drawer_notebooks));
        drawerListItems.add(getString(R.string.drawer_search));
        mNavDrawerListView.setAdapter(new DrawerListAdapter(this, drawerListItems));
        mNavDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case UPLOAD_INDEX: {
                        Intent intent = new Intent(DrawerActivity.this, UploadNotesActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case NOTEBOOKS_INDEX: {
                        Intent intent = new Intent(DrawerActivity.this, NotebookFeedActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case SEARCH_INDEX: {
                        Intent intent = new Intent(DrawerActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
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
