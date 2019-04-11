package com.example.mm.sofraappmaster.helper.fragment_back_pressed;

import android.support.v4.app.FragmentActivity;

public class BackPressedListener implements OnBackPressedListener{

    private final FragmentActivity activity;

    public BackPressedListener(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void doBack() {
        if (!activity.getSupportFragmentManager().popBackStackImmediate()) {
            activity.supportFinishAfterTransition();
        }
    }
}
