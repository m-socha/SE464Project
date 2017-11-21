package com.example.michael.watnotes.activities.core;

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
                serviceFragmentTask.performTask((A) baseActivity.getServiceFragment());
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
                uiFragmentTask.performTask((A) baseActivity.getUiFragment());
            }
        });
    }

    protected interface ActivityTask<A extends BaseActivity> {
        void performTask(A baseActivity);
    }

    protected <A extends BaseActivity> void withActivity(ActivityTask<A> activityTask) {
        activityTask.performTask((A) getActivity());
    }
}
