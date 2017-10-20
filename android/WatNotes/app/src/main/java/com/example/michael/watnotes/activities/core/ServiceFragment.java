package com.example.michael.watnotes.activities.core;

import com.example.michael.watnotes.api.standalone.UploadNoteService;

/**
 * Created by michael on 10/1/17.
 */

public class ServiceFragment extends WatNotesFragment {

    private UploadNoteService mUploadNoteService = new UploadNoteService();

    public void uploadNoteFile(int notebookId, String uri, String mimeType, byte[] fileContents) {
        mUploadNoteService.requestService(notebookId, uri, mimeType, fileContents);
    }

    @Override
    public void onStop() {
        super.onStop();

        mUploadNoteService.cancelAllRequests();
    }

}
