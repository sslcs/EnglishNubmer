package com.reven.englishnumber.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reven.englishnumber.R;
import com.reven.englishnumber.util.StoreUtil;

/**
 * Level Fragment
 */
public class LevelFragment extends BaseFragment implements View.OnClickListener {
    // 0:English  1:German
    private int mLanguage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        initLayout(view);
        return view;
    }

    private void initLayout(final View view) {
        View vLevel0 = view.findViewById(R.id.btn_level_0);
        View vLevel1 = view.findViewById(R.id.btn_level_1);
        View vLevel2 = view.findViewById(R.id.btn_level_2);
        View vLevel3 = view.findViewById(R.id.btn_level_3);
        View vLevel4 = view.findViewById(R.id.btn_level_4);
        vLevel0.setTag(0);
        vLevel1.setTag(1);
        vLevel2.setTag(2);
        vLevel3.setTag(3);
        vLevel4.setTag(4);
        vLevel0.setOnClickListener(this);
        vLevel1.setOnClickListener(this);
        vLevel2.setOnClickListener(this);
        vLevel3.setOnClickListener(this);
        vLevel4.setOnClickListener(this);

        final StoreUtil store = new StoreUtil(getActivity());
        final View btnEnglish = view.findViewById(R.id.btn_english);
        final View btnGerman = view.findViewById(R.id.btn_german);
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLanguage = 0;
                store.putInt("language", mLanguage);
                btnEnglish.setBackgroundColor(0xFF669900);
                btnGerman.setBackgroundColor(0xFFAAAAAA);
            }
        });
        btnGerman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLanguage = 1;
                store.putInt("language", mLanguage);
                btnEnglish.setBackgroundColor(0xFFAAAAAA);
                btnGerman.setBackgroundColor(0xFF669900);
            }
        });
        mLanguage = store.getInt("language", 1);
        if (mLanguage == 0) {
            btnEnglish.performClick();
        }
    }

    @Override
    public void onClick(View v) {
        int level = (int) v.getTag();
        MainFragment fragment = MainFragment.newInstance(level, mLanguage);
        mHost.replace(fragment, true);
    }
}
