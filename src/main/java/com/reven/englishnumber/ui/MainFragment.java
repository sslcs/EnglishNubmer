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
    private int mLevel = 0;
    private int mLanguage = 1;
    private int mCountRight, mCountWrong;

    private TextView tvNumber, tvRight, tvWrong;
    private View vChoices, vInfo;
    private Button btn_0, btn_1, btn_2, btn_3, btnStart;
    private Chronometer mChronometer;

    private String[] strLevel = {"学渣", "学酥", "学民", "学霸", "学神"};
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
        setChoices();
        mNextAnimator.start();
    }

    private int getRandomNumber() {
        switch (mLevel) {
            case 0:
                return mRandom.nextInt(11);
            case 1:
                return mRandom.nextInt(89) + 11;
            case 2:
                return mRandom.nextInt(999900) + 100;
            case 3:
                return mRandom.nextInt(999000000) + 1000000;
            default:
                return mRandom.nextInt(Integer.MAX_VALUE);
        }
    }

    private void setChoices() {
        ArrayList<String> choices = new ArrayList<>(4);
        choices.add(mNumber + "");
        if (mNumber < 11) {
            getRandom10(choices);
        } else if (mNumber < 100) {
            getRandom100(choices);
        } else {
            getRandom1000(choices);
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

    private void getRandom10(ArrayList<String> choices) {
        while (choices.size() != 4) {
            int tmp = mRandom.nextInt(11);
            int size = choices.size();
            int j = 0;
            for (; j < size; j++) {
                if (Integer.toString(tmp).equals(choices.get(j))) {
                    break;
                }
            }
            if (j == size) {
                choices.add(tmp + "");
            }
        }
    }

    private void getRandom100(ArrayList<String> choices) {
        int decade = mNumber / 10;
        int rest = mNumber % 10;
        int tmpDecade, tmpRest;
        while (true) {
            tmpDecade = mRandom.nextInt(9) + 1;
            if (tmpDecade != decade) {
                choices.add(Integer.toString(tmpDecade * 10 + rest));
                break;
            }
        }
        while (true) {
            tmpRest = mRandom.nextInt(10);
            if (tmpRest != rest) {
                choices.add(Integer.toString(mNumber - rest + tmpRest));
                choices.add(Integer.toString(tmpDecade * 10 + tmpRest));
                break;
            }
        }
    }

    private void getRandom1000(ArrayList<String> choices) {
        String strNumber = Integer.toString(mNumber);
        int first = Integer.valueOf(strNumber.substring(0, 1));
        int last = mNumber % 10;
        int tmp;
        while (true) {
            tmp = mRandom.nextInt(9) + 1;
            if (tmp != first) {
                StringBuilder sb = new StringBuilder(strNumber);
                sb.replace(0, 1, tmp + "");
                choices.add(sb.toString());
                break;
            }
        }
        while (true) {
            tmp = mRandom.nextInt(10);
            if (tmp != last) {
                choices.add(String.valueOf(mNumber - last + tmp));
                StringBuilder sb = new StringBuilder(choices.get(1));
                choices.add(sb.replace(sb.length() - 1, sb.length(), tmp + "").toString());
                break;
            }
        }
    }

    private void stop() {
        tvNumber.setText(strLevel[mLevel] + "加油!!!");
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
        String number = (String) v.getTag();
        if (String.valueOf(mNumber).equals(number)) {
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
