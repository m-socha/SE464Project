package com.example.michael.watnotes.activities;

import android.app.Fragment;

/**
 * Created by michael on 10/11/17.
 */

public class WatNotesFragment extends Fragment {

    protected interface ServiceFragmentTask<A extends ServiceFragment> {
        void performTask(A serviceFragment);
    }

    protected <A extends ServiceFragment> void withServiceFragment(final ServiceFragmentTask<A> serviceFragmentTask) {
        withActivity(new ActivityTask<BaseActivity>() {
            @Override
            public void performTask(BaseActivity baseActivity) {
                A castedServiceFragment = null;
                try {
                    baseActivity.getServiceFragment();
                } catch (ClassCastException e) {}

                if (castedServiceFragment != null) {
                    serviceFragmentTask.performTask(castedServiceFragment);
                }
            }
        });
    }

    protected interface UiFragmentTask<A extends UiFragment> {
        void performTask(A uiFragment);
    }

    protected <A extends UiFragment> void withUiFragment(final UiFragmentTask<A> uiFragmentTask) {
        withActivity(new ActivityTask<BaseActivity>() {
            @Override
            public void performTask(BaseActivity baseActivity) {
                A castedUiFragment = null;
                try {
                    baseActivity.getUiFragment();
                } catch (ClassCastException e) {}

                if (castedUiFragment != null) {
                    uiFragmentTask.performTask(castedUiFragment);
                }
            }
        });
    }

    protected interface ActivityTask<A extends BaseActivity> {
        void performTask(A baseActivity);
    }

    protected <A extends BaseActivity> void withActivity(ActivityTask<A> activityTask) {
        A castedActivity = null;
        try {
            castedActivity = (A) getActivity();
        } catch (ClassCastException e) {}

        if (castedActivity != null) {
            activityTask.performTask(castedActivity);
        }
    }
}
