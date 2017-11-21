package com.example.michael.watnotes.activities.search;

import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.api.standalone.SearchNoteService;

/**
 * Created by michael on 11/21/17.
 */

public class SearchServiceFragment extends ServiceFragment {

    private SearchNoteService mSearchNoteService = new SearchNoteService();

    public void search(String query) {
        mSearchNoteService.requestService(query, null, null);
    }
}
