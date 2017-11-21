package com.example.michael.watnotes.feed.notebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.model.Notebook;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedView extends RelativeLayout {

    private ListView mNotebookListView;

    public NotebookFeedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public NotebookFeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public NotebookFeedView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        LayoutInflater.from(getContext()).inflate(R.layout.notebook_feed_view, this);
        mNotebookListView = (ListView) findViewById(R.id.notebook_list_view);
    }

    public void setup(List<Notebook> notebooks) {
        mNotebookListView.setAdapter(new NotebookAdapter(getContext(), notebooks));
    }
}
