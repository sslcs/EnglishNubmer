package com.reven.englishnumber.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.reven.englishnumber.R;
import com.reven.englishnumber.util.NumberUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Main Fragment
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    private Random mRandom;
    private int mNumber;
    private int mLevel = 100;
    private int mLanguage = 1;
    private int mCountRight, mCountWrong;

    private TextView tvNumber, tvRight, tvWrong;
    private View vChoices, vInfo;
    private Button btn_0, btn_1, btn_2, btn_3, btnStart;
    private Chronometer mChronometer;

    private ObjectAnimator mNextAnimator;

    public static MainFragment newInstance(int level, int language) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        bundle.putInt("language", language);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRandom = new Random(System.currentTimeMillis());
        mLevel = getArguments().getInt("level");
        mLanguage = getArguments().getInt("language");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initLayout(view);
        return view;
    }

    private void initLayout(final View view) {
        vChoices = view.findViewById(R.id.v_choices);
        vInfo = view.findViewById(R.id.v_info);
        mChronometer = (Chronometer) view.findViewById(R.id.chronometer);
        mChronometer.setFormat("时间：%s");

        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvWrong = (TextView) view.findViewById(R.id.tv_wrong);
        tvNumber = (TextView) view.findViewById(R.id.tv_number);
        String[] strLevel = {"学渣", "学酥", "学民", "学霸", "学神"};
        tvNumber.setText(strLevel[mLevel] + "加油!!!");

        final View btnBack = view.findViewById(R.id.btn_back);

        btnStart = (Button) view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().equals("开始")) {
                    btnBack.setVisibility(View.GONE);
                    btnStart.setText("停止");
                    start();
                } else {
                    btnBack.setVisibility(View.VISIBLE);
                    btnStart.setText("开始");
                    stop();
                }
                btnStart.setEnabled(false);
                btnStart.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnStart.setEnabled(true);
                    }
                }, 3000);
            }
        });

        btn_0 = (Button) view.findViewById(R.id.btn_0);
        btn_1 = (Button) view.findViewById(R.id.btn_1);
        btn_2 = (Button) view.findViewById(R.id.btn_2);
        btn_3 = (Button) view.findViewById(R.id.btn_3);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);

        mNextAnimator = ObjectAnimator.ofFloat(tvNumber, "alpha", 0.5f, 1);
    }

    private void next() {
        mNumber = getRandomNumber();
        tvNumber.setText(NumberUtil.getNumberString(mNumber, mLanguage));
        setChoices(mNumber);
        mNextAnimator.start();
    }

    private int getRandomNumber() {
        switch (mLevel) {
            case 0:
                return mRandom.nextInt(10);
            case 1:
                return mRandom.nextInt(20);
            case 2:
                return mRandom.nextInt(80) + 20;
            case 3:
                return mRandom.nextInt(999999900) + 100;
        }
        return mRandom.nextInt(Integer.MAX_VALUE);
    }

    private void setChoices(int number) {
        ArrayList<Integer> choices = new ArrayList<>(4);
        choices.add(number);
        int delta;
        if (number < 10) {
            delta = 0;
        } else {
            delta = number - 10;
        }
        for (int i = 1; i < 4; i++) {
            while (true) {
                int tmp = mRandom.nextInt(10) + delta;
                int j = 0;
                for (; j < i; j++) {
                    if (tmp == choices.get(j)) {
                        break;
                    }
                }
                if (j == i) {
                    choices.add(tmp);
                    break;
                }
            }
        }
        Collections.shuffle(choices);
        btn_0.setText(NumberUtil.getNumber(choices.get(0)));
        btn_1.setText(NumberUtil.getNumber(choices.get(1)));
        btn_2.setText(NumberUtil.getNumber(choices.get(2)));
        btn_3.setText(NumberUtil.getNumber(choices.get(3)));
        btn_0.setTag(choices.get(0));
        btn_1.setTag(choices.get(1));
        btn_2.setTag(choices.get(2));
        btn_3.setTag(choices.get(3));
    }

    private void stop() {
        mChronometer.stop();
        vChoices.setVisibility(View.INVISIBLE);
        tvNumber.setText("");
    }

    private void start() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mCountRight = 0;
        mCountWrong = 0;
        tvRight.setText("正确：0");
        tvWrong.setText("错误：0");
        final AnimatorListenerAdapter listenerGo = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                vChoices.setVisibility(View.VISIBLE);
                vInfo.setVisibility(View.VISIBLE);
                tvNumber.setAlpha(1);
                tvNumber.setScaleX(1);
                tvNumber.setScaleY(1);

                mChronometer.start();
                next();
            }
        };
        final AnimatorListenerAdapter listener1 = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playStartAnimation("GO!", listenerGo);
            }
        };
        final AnimatorListenerAdapter listener2 = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playStartAnimation("1", listener1);
            }
        };
        final AnimatorListenerAdapter listener3 = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playStartAnimation("2", listener2);
            }
        };
        playStartAnimation("3", listener3);
    }

    private void playStartAnimation(String content, AnimatorListenerAdapter listener) {
        tvNumber.setText(content);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvNumber, "scaleX", 1, 2);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvNumber, "scaleY", 1, 2);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tvNumber, "alpha", 1, 0.2f);
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setDuration(500);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        int number = (int) v.getTag();
        if (mNumber == number) {
            next();
            mHost.playSound(R.raw.right);
            tvRight.setText("正确：" + (++mCountRight));
        } else {
            ShakeAnimator.shake(v);
            mHost.playSound(R.raw.wrong);
            tvWrong.setText("错误：" + (++mCountWrong));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mChronometer.stop();
    }
}
