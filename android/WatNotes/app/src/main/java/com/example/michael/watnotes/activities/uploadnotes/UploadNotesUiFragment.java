package com.example.michael.watnotes.activities.uploadnotes;

import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.UiFragment;
import com.example.michael.watnotes.uicomponents.FileUploadView;
import com.example.michael.watnotes.uicomponents.TakePhotoView;

/**
 * Created by michael on 10/1/17.
 */

public class UploadNotesUiFragment extends UiFragment {

    TakePhotoView mTakePhotoView;
    FileUploadView mFileUploadView;

    @Override
    protected int getLayoutId() {
        return R.layout.upload_notes_ui_fragment;
    }

    @Override
    protected void initializeUi(View view) {
        mTakePhotoView = (TakePhotoView) view.findViewById(R.id.take_photo_view);
        mFileUploadView = (FileUploadView) view.findViewById(R.id.file_upload_view);
    }

}
