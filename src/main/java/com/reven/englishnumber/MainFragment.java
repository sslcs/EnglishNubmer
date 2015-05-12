package com.reven.englishnumber;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    private ObjectAnimator mAnimator;
    private Random mRandom;
    private TextView tvNumber;
    private int mDuration = 2000;

    public MainFragment() {
        mRandom = new Random(System.currentTimeMillis());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initLayout(view);
        return view;
    }

    private void initLayout(final View view) {
        tvNumber = (TextView) view.findViewById(R.id.tv_number);
        tvNumber.post(new Runnable() {
            @Override
            public void run() {
                mAnimator = ObjectAnimator.ofFloat(tvNumber, "translationY", 0, view.getHeight());
                mAnimator.setInterpolator(new LinearInterpolator());
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        next();
                    }
                });
            }
        });

        View btnStart = view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNumber.post(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                });
            }
        });
    }

    private void next() {
        int number = mRandom.nextInt(100);
        tvNumber.setText(String.valueOf(number));
        if (mDuration > 500) {
            mDuration -= 100;
        }
        mAnimator.setDuration(mDuration);
        mAnimator.start();
        DebugLog.e("duration : " + mDuration);
    }
}
