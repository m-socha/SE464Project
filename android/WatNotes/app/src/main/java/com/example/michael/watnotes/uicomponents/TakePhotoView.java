package com.example.michael.watnotes.uicomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.michael.watnotes.R;

/**
 * Created by michael on 10/11/17.
 */

public class TakePhotoView extends RelativeLayout {

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
    }

    public void setup(String prompt, int photoIcon) {
        mPromptTextView.setText(prompt);
        mPhotoIconImageView.setImageResource(photoIcon);
    }
}
