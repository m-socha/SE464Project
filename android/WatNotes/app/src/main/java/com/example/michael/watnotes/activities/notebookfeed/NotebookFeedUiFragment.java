package com.example.michael.watnotes.activities.notebookfeed;

import android.content.Intent;
import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;
import com.example.michael.watnotes.activities.core.UiFragment;
import com.example.michael.watnotes.activities.notefeed.NoteFeedActivity;
import com.example.michael.watnotes.activities.notefeed.NoteFeedServiceFragment;
import com.example.michael.watnotes.api.model.Notebook;
import com.example.michael.watnotes.feed.notebook.NotebookFeedView;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedUiFragment extends UiFragment {
    private NotebookFeedView mFeedView;

    @Override
    protected int getLayoutId() {
        return R.layout.notebook_ui_fragment;
    }

    @Override
    protected void initializeUi(View view) {
        mFeedView = (NotebookFeedView) view.findViewById(R.id.notebook_feed_view);

        mFeedView.setOnClickCallback(new NotebookFeedView.NotebookFeedViewOnClickCallback() {
            @Override
            public void onNotebookClicked(final Notebook notebook) {
                withActivity(new ActivityTask<BaseActivity>() {
                    @Override
                    public void performTask(BaseActivity baseActivity) {
                        Intent intent = new Intent(baseActivity, NoteFeedActivity.class);
                        intent.putExtra(NoteFeedActivity.NOTEBOOK_ID_KEY, notebook.getId());
                        startActivity(intent);
                    }
                });
            }
        });

        withServiceFragment(new ServiceFragmentTask<NotebookFeedServiceFragment>() {
            @Override
            public void performTask(final NotebookFeedServiceFragment serviceFragment) {
                withActivity(new ActivityTask<NotebookFeedActivity>() {
                    @Override
                    public void performTask(NotebookFeedActivity baseActivity) {
                        serviceFragment.loadUserNotebooks(baseActivity.getUserId());
                    }
                });
            }
        });
    }

    public void setup(List<Notebook> notebooks) {
        mFeedView.setup(notebooks);
    }
}
