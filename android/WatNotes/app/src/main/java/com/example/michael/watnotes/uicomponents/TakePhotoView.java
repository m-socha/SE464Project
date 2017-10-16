package com.example.michael.watnotes.uicomponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.provider.MediaStore;
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

public class TakePhotoView extends RelativeLayout {

    private BaseActivity mActivity;
    private TextView mPromptTextView;
    private ImageView mPhotoIconImageView;

    public TakePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public TakePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public TakePhotoView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        LayoutInflater.from(getContext()).inflate(R.layout.take_photo_view, this);

        mPromptTextView = (TextView) findViewById(R.id.prompt_text_view);
        mPhotoIconImageView = (ImageView) findViewById(R.id.photo_icon_image_view);

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
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(cameraIntent, BaseActivity.ActivityResult.NOTE_CAMERA_RESULT.getValue());
    }

    public void setup(BaseActivity activity, String prompt, int photoIcon) {
        mActivity = activity;

        mPromptTextView.setText(prompt);
        mPhotoIconImageView.setImageResource(photoIcon);
    }
}
