package com.example.michael.watnotes.uicomponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.BaseActivity;

/**
 * Created by michael on 10/11/17.
 */

public class FileUploadView extends RelativeLayout {

    private BaseActivity.FileSearchType mFileSearchType =  BaseActivity.FileSearchType.GENERAL;

    private BaseActivity mActivity;
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

        setBackgroundColor(getResources().getColor(R.color.gray));
        setPadding(
                getResources().getDimensionPixelSize(R.dimen.half_standard_margin),
                getResources().getDimensionPixelSize(R.dimen.half_standard_margin),
                getResources().getDimensionPixelSize(R.dimen.half_standard_margin),
                getResources().getDimensionPixelSize(R.dimen.half_standard_margin)
        );

        GradientDrawable roundedCornerBackground = new GradientDrawable();
        roundedCornerBackground.setShape(GradientDrawable.RECTANGLE);
        roundedCornerBackground.setColor(ContextCompat.getColor(getContext(), R.color.gray_light));
        roundedCornerBackground.setStroke(getResources().getDimensionPixelSize(R.dimen.border_width_thick), ContextCompat.getColor(getContext(), R.color.gray_light));
        roundedCornerBackground.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.corner_radius_small));
        setBackground(roundedCornerBackground);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.selectNoteFile(mFileSearchType);
            }
        });
    }

    public void setup(BaseActivity activity, String prompt, int uploadIcon) {
        setup(activity, prompt, uploadIcon, null);
    }

    public void setup(BaseActivity activity, String prompt, int uploadIcon, String caption) {
        mActivity = activity;

        mPromptTextView.setText(prompt);
        mUploadImageView.setImageResource(uploadIcon);

        if (caption != null) {
            mCaptionTextView.setText(caption);
            mCaptionTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setFileSearchType(BaseActivity.FileSearchType fileSearchType) {
        mFileSearchType = fileSearchType;
    }
}
