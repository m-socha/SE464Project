package com.example.michael.watnotes.activities.uploadnotes;

import android.view.View;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;
import com.example.michael.watnotes.activities.core.UiFragment;
import com.example.michael.watnotes.uicomponents.FileUploadView;
import com.example.michael.watnotes.uicomponents.TakePhotoView;

/**
 * Created by michael on 10/1/17.
 */

public class UploadNotesUiFragment extends UiFragment {

    private TakePhotoView mTakePhotoView;
    private FileUploadView mImageUploadView;
    private FileUploadView mFileUploadView;

    @Override
    protected int getLayoutId() {
        return R.layout.upload_notes_ui_fragment;
    }

    @Override
    protected void initializeUi(View view) {
        mTakePhotoView = (TakePhotoView) view.findViewById(R.id.take_photo_view);
        mImageUploadView = (FileUploadView) view.findViewById(R.id.image_upload_view);
        mFileUploadView = (FileUploadView) view.findViewById(R.id.file_upload_view);

        withActivity(new ActivityTask<BaseActivity>() {
            @Override
            public void performTask(BaseActivity baseActivity) {
                mTakePhotoView.setup(baseActivity, getString(R.string.upload_notes_take_photo_prompt), R.drawable.camera_icon);

                mImageUploadView.setup(baseActivity, getString(R.string.upload_notes_upload_image_prompt), R.drawable.gallery_icon);
                mImageUploadView.setFileSearchType(BaseActivity.FileSearchType.IMAGE);

                mFileUploadView.setup(baseActivity, getString(R.string.upload_notes_upload_file_prompt), R.drawable.note_icon, getString(R.string.upload_notes_upload_file_caption));
                mFileUploadView.setFileSearchType(BaseActivity.FileSearchType.GENERAL);
            }
        });
    }

}
