package com.example.michael.watnotes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Created by michael on 10/1/17.
 */

public abstract class BaseActivity extends Activity {

    public enum ActivityResult {
        NOTE_CAMERA_RESULT(1),
        NOTE_FILE_SELECTION_RESULT(2);

        private int mActivityResultId;

        ActivityResult(int activityResultId) {
            mActivityResultId = activityResultId;
        }

        public int getValue() {
            return mActivityResultId;
        }
    }

    public enum FileSearchType {
        GENERAL("file/*"),
        IMAGE("image/*");

        private String mSearchType;

        FileSearchType(String searchType) {
            mSearchType = searchType;
        }

        public String getSearchType() {
            return mSearchType;
        }
    };

    protected static final String UI_FRAGMENT_TAG = "UiFragmentTag";
    protected static final String SERVICE_FRAGMENT_TAG = "ServiceFragmentTag";

    private UiFragment mUiFragment;
    private ServiceFragment mServiceFragment;

    protected abstract UiFragment createUiFragment();
    protected abstract ServiceFragment createServiceFragment();
    protected abstract int getLayoutId();
    protected abstract int getUiFragmentContainerView();
    protected abstract String getActionBarTitle();

    UiFragment getUiFragment() {
        return mUiFragment;
    }

    ServiceFragment getServiceFragment() {
        return mServiceFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        mUiFragment = createUiFragment();
        mServiceFragment = createServiceFragment();

        attachUiFragment(mUiFragment);
        attachServiceFragment(mServiceFragment);
    }

    protected void attachUiFragment(UiFragment uiFragment) {
        getFragmentManager()
                .beginTransaction()
                .add(getUiFragmentContainerView(), uiFragment, BaseActivity.UI_FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }

    protected void attachServiceFragment(ServiceFragment serviceFragment) {
        getFragmentManager()
                .beginTransaction()
                .add(serviceFragment, BaseActivity.SERVICE_FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityResult.NOTE_CAMERA_RESULT.getValue()) {
            handleNoteCameraResult(resultCode);
        } else if (requestCode == ActivityResult.NOTE_FILE_SELECTION_RESULT.getValue()) {
            handleNoteFileSelectionResult(resultCode);
        }
    }

    public void selectNoteFile(FileSearchType fileSearchType) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(fileSearchType.getSearchType());
        startActivityForResult(intent, ActivityResult.NOTE_FILE_SELECTION_RESULT.getValue());
    }

    private void handleNoteCameraResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            selectNoteFile(FileSearchType.IMAGE);
        }
    }

    private void handleNoteFileSelectionResult(int resultCode) {
        if (resultCode == RESULT_OK) {

        }
    }
}
