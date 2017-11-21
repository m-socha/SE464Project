package com.example.michael.watnotes.activities.notefeed;

import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.UiFragment;
import com.example.michael.watnotes.feed.note.NoteFeedView;

/**
 * Created by michael on 11/21/17.
 */

public class NoteFeedUiFragment extends UiFragment {
    private NoteFeedView mFeedView;

    @Override
    protected int getLayoutId() {
        return R.layout.note_feed_ui_fragment;
    }

    @Override
    protected void initializeUi(View view) {
        mFeedView = (NoteFeedView) view.findViewById(R.id.note_feed_view);
    }
}
