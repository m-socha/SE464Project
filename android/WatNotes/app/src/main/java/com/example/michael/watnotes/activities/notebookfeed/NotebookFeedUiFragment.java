package com.example.michael.watnotes.activities.notebookfeed;

import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.UiFragment;
import com.example.michael.watnotes.feed.notebook.NotebookFeedView;

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
    }

}
