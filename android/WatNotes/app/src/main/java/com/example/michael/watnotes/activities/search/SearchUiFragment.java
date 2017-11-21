package com.example.michael.watnotes.activities.search;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.michael.watnotes.R;
import com.example.michael.watnotes.activities.core.BaseActivity;
import com.example.michael.watnotes.activities.core.ServiceFragment;
import com.example.michael.watnotes.activities.core.UiFragment;

/**
 * Created by michael on 11/21/17.
 */

public class SearchUiFragment extends UiFragment {

    private EditText mSearchBox;
    private Button mSearchGoButton;

    @Override
    protected int getLayoutId() {
        return R.layout.search_ui_fragment;
    }

    @Override
    protected void initializeUi(View view) {
        mSearchBox = (EditText) view.findViewById(R.id.search_search_box);
        mSearchGoButton = (Button) view.findViewById(R.id.search_go_button);

        mSearchGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String query = mSearchBox.getText().toString().trim();
                withServiceFragment(new ServiceFragmentTask<SearchServiceFragment>() {
                    @Override
                    public void performTask(SearchServiceFragment serviceFragment) {
                        serviceFragment.search(query);
                    }
                });
            }
        });
    }
}
