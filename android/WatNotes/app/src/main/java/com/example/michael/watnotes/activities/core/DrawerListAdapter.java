package com.example.michael.watnotes.activities.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.michael.watnotes.R;

import java.util.List;

/**
 * Created by michael on 11/21/17.
 */

public class DrawerListAdapter extends ArrayAdapter<String> {

    List<String> mItems;

    public DrawerListAdapter(Context context, List<String> items) {
        super(context, R.layout.drawer_item_layout, items);
        mItems = items;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.drawer_item_layout, null);
        }

        String item = mItems.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.drawer_list_text_view);
        textView.setText(item);
        return convertView;
    }

}
