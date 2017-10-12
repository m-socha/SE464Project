package com.example.michael.watnotes.uicomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.michael.watnotes.R;

/**
 * Created by michael on 10/11/17.
 */

public class FileUploadView extends RelativeLayout {

    private TextView mPromptTextView;
    private ImageView mUploadImageView;
    private TextView mCaptionTextView;

    public FileUploadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public FileUploadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public FileUploadView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        LayoutInflater.from(getContext()).inflate(R.layout.file_upload_view, this);

        mPromptTextView = (TextView) findViewById(R.id.prompt_text_view);
        mUploadImageView = (ImageView) findViewById(R.id.upload_image_view);
        mCaptionTextView = (TextView) findViewById(R.id.caption_text_view);
    }

    public void setup(String prompt, int uploadIcon, String caption) {
        mPromptTextView.setText(prompt);
        mUploadImageView.setImageResource(uploadIcon);
        mCaptionTextView.setText(caption);
    }

}
