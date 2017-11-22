package com.example.michael.watnotes.activities.notebookfeed;

import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.api.model.Notebook;
import com.example.michael.watnotes.api.standalone.UserNotebooksService;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedServiceFragment extends ServiceFragment {

    private UserNotebooksService mUserNotebooksService = new UserNotebooksService();

    public void loadUserNotebooks(int userId) {
        mUserNotebooksService.requestService(userId, new UserNotebooksService.UserNotebooksCompletionCallback() {
            @Override
            public void onSuccess(final List<Notebook> notebooks) {
                withUiFragment(new UiFragmentTask<NotebookFeedUiFragment>() {
                    @Override
                    public void performTask(NotebookFeedUiFragment uiFragment) {
                        uiFragment.setup(notebooks);
                    }
                });
            }
        }, null);
    }
}
