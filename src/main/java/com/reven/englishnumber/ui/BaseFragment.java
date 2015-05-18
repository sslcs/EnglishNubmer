package com.reven.englishnumber.ui;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by CS on 2015/5/18.
 * Cast activity to MainActivity
 */
public class BaseFragment extends Fragment {
    protected MainActivity mHost;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHost = (MainActivity) activity;
    }
}
