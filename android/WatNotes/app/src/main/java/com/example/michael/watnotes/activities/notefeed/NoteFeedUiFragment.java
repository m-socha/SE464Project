package com.example.michael.watnotes.activities.notefeed;

import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;
import com.example.michael.watnotes.api.model.Note;
import com.example.michael.watnotes.feed.note.NoteFeedView;

import java.util.List;

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
        withServiceFragment(new ServiceFragmentTask<NoteFeedServiceFragment>() {
            @Override
            public void performTask(final NoteFeedServiceFragment serviceFragment) {
                withActivity(new ActivityTask<NoteFeedActivity>() {
                    @Override
                    public void performTask(NoteFeedActivity baseActivity) {
                        serviceFragment.searchNotes(baseActivity.getQuery());
                    }
                });
            }
        });
    }

    public void setup(List<Note> notes) {
        mFeedView.setup(notes);
    }
}
