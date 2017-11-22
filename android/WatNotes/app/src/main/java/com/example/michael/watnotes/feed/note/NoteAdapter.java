package com.example.michael.watnotes.feed.note;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private List<Note> mNotes = new ArrayList();

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, R.layout.note_item, notes);
        mNotes = notes;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.note_item, null);
        }

        Note note = mNotes.get(position);

        if (note.getFormat().equals("text/plain")) {
            String data = note.getData();
            TextView dataTextView = (TextView) convertView.findViewById(R.id.note_data);
            dataTextView.setText(data);
        }

        return convertView;
    }
}
