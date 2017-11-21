package com.example.michael.watnotes.feed.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.model.Note;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NoteFeedView extends RelativeLayout {

    private ListView mNoteListView;

    public NoteFeedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public NoteFeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public NoteFeedView(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        LayoutInflater.from(getContext()).inflate(R.layout.note_feed_view, this);
        mNoteListView = (ListView) findViewById(R.id.note_list_view);
    }

    public void setup(List<Note> notes) {
        mNoteListView.setAdapter(new NoteAdapter(getContext(), notes));
    }
}
