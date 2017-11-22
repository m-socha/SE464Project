package com.example.michael.watnotes.feed.notebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.model.Notebook;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookFeedView extends RelativeLayout {

    public interface NotebookFeedViewOnClickCallback {
        void onNotebookClicked(Notebook notebook);
    }

    private List<Notebook> mNotebooks;
    private NotebookFeedViewOnClickCallback mClickCallback;

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
        mNotebooks = notebooks;
        mNotebookListView.setAdapter(new NotebookAdapter(getContext(), notebooks));
        mNotebookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mClickCallback != null) {
                    Notebook notebook = mNotebooks.get(position);
                    mClickCallback.onNotebookClicked(notebook);
                }
            }
        });
    }

    public void setOnClickCallback(NotebookFeedViewOnClickCallback clickCallback) {
        mClickCallback = clickCallback;
    }
}
