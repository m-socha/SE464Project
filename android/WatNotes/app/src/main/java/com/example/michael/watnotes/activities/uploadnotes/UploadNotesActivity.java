package com.example.michael.watnotes.activities.uploadnotes;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.DrawerActivity;
import com.example.michael.watnotes.activities.ServiceFragment;
import com.example.michael.watnotes.activities.UiFragment;

public class UploadNotesActivity extends DrawerActivity {

    @Override
    protected UiFragment createUiFragment() {
        return new UploadNotesUiFragment();
    }

    @Override
    protected ServiceFragment createServiceFragment() {
        return new UploadNotesServiceFragment();
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.upload_notes_title);
    }

}
