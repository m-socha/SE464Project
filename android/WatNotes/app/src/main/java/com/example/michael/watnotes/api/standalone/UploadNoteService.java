package com.example.michael.watnotes.api.standalone;

import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.core.SingleApiService;
import com.example.michael.watnotes.util.IOUtil;

/**
 * Created by michael on 10/18/17.
 */

public class UploadNoteService extends SingleApiService {

    public void requestService(int notebookId, IOUtil.FileInfo fileInfo, String fileFormat) {
        ApiRequest request = new ApiRequest("notebooks/" + notebookId + "/notes?form=1");
        request.addFormFile("data", fileInfo);
        request.addParam("format", fileFormat);
        request.startRequest();
    }

}
