package com.example.michael.watnotes.api.standalone;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.SingleApiService;

/**
 * Created by michael on 10/18/17.
 */

public class UploadNoteService extends SingleApiService {

    public void requestService(int notebookId, String fileUri, String fileFormat, byte[] fileContents) {
        ApiRequest request = new ApiRequest("notebooks/" + notebookId + "/notes?form=1");
        request.addFormFile("data", fileUri, fileFormat, fileContents);
        request.startRequest();
    }

}
