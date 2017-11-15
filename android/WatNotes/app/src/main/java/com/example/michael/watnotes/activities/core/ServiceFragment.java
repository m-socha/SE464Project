package com.example.michael.watnotes.activities.core;

import com.example.michael.watnotes.api.standalone.UploadNoteService;
import com.example.michael.watnotes.util.IOUtil;

import java.io.File;

/**
 * Created by michael on 10/1/17.
 */

public class ServiceFragment extends WatNotesFragment {

    private UploadNoteService mUploadNoteService = new UploadNoteService();

    public void uploadNoteFile(int notebookId, IOUtil.FileInfo fileInfo, String fileFormat) {
        mUploadNoteService.requestService(notebookId, fileInfo, fileFormat);
    }

    @Override
    public void onStop() {
        super.onStop();

        mUploadNoteService.cancelAllRequests();
    }

}
