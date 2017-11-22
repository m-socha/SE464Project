package com.example.michael.watnotes.activities.notefeed;

import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.api.model.Note;
import com.example.michael.watnotes.api.standalone.NotebookNotesService;
import com.example.michael.watnotes.api.standalone.SearchNoteService;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NoteFeedServiceFragment extends ServiceFragment {
    private SearchNoteService mSearchNoteService = new SearchNoteService();
    private NotebookNotesService mNotebookNotesService = new NotebookNotesService();

    public void searchNotes(String query) {
        mSearchNoteService.requestService(query, new SearchNoteService.SearchNoteCompletionCallback() {
            @Override
            public void onSuccess(final List<Note> notes) {
                withUiFragment(new UiFragmentTask<NoteFeedUiFragment>() {
                    @Override
                    public void performTask(NoteFeedUiFragment uiFragment) {
                        uiFragment.setup(notes);
                    }
                });
            }
        }, null);
    }

    public void loadNotebookNotes(int notebookId) {
        mNotebookNotesService.requestService(notebookId, new SearchNoteService.SearchNoteCompletionCallback() {
            @Override
            public void onSuccess(final List<Note> notes) {
                withUiFragment(new UiFragmentTask<NoteFeedUiFragment>() {
                    @Override
                    public void performTask(NoteFeedUiFragment uiFragment) {
                        uiFragment.setup(notes);
                    }
                });
            }
        }, null);
    }
}
