package com.example.michael.watnotes.feed.note;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.core.ApiRequest;
import com.example.michael.watnotes.api.model.Note;
import com.squareup.picasso.Picasso;

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

        TextView dataTextView = (TextView) convertView.findViewById(R.id.note_data);
        dataTextView.setVisibility(View.GONE);

        ImageView noteImageView = (ImageView) convertView.findViewById(R.id.note_image_view);
        noteImageView.setVisibility(View.GONE);

        if (note.getFormat().equals("text/plain")) {
            String data = note.getData();
            dataTextView.setVisibility(View.VISIBLE);
            dataTextView.setText(data);
        } else if (note.getFormat().equals("image/jpeg")) {
            noteImageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(ApiRequest.BASE_URL + "notes/" + note.getId() + "." + note.getExtension()).into(noteImageView);
        }

        return convertView;
    }
}
