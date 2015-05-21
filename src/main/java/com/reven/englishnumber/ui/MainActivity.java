package com.reven.englishnumber.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.reven.englishnumber.R;

public class MainActivity extends SoundActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    private void initLayout() {
        replace(new LevelFragment(), false);
    }

    public void replace(Fragment fragment, boolean enableBack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        if (enableBack) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
    }
}
