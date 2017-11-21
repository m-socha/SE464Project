package com.example.michael.watnotes.feed.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.api.model.Course;
import com.example.michael.watnotes.api.model.Notebook;
import com.example.michael.watnotes.api.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class NotebookAdapter extends ArrayAdapter<Notebook> {

    private List<Notebook> mNotebooks = new ArrayList();

    public NotebookAdapter(Context context, List<Notebook> notebooks) {
        super(context, R.layout.notebook_item, notebooks);
        mNotebooks = notebooks;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.notebook_item, null);
        }

        Notebook notebook = mNotebooks.get(position);

        User user = notebook.getUser();
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.notebook_user_name);
        userNameTextView.setText(user.getName());

        Course course = notebook.getCourse();
        TextView courseCodeTextView = (TextView) convertView.findViewById(R.id.notebook_course_code);
        courseCodeTextView.setText(course.getCode());
        TextView courseNameTextView = (TextView) convertView.findViewById(R.id.notebook_course_name);
        courseNameTextView.setText(course.getTitle());

        return convertView;
    }
}
