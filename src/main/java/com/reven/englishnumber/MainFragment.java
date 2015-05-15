package com.reven.englishnumber;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private Random mRandom;
    private int mNumber;
    private int mLevel = 100;

    private TextView tvNumber;
    private View btnStart, vChoices;
    private Button btn_0, btn_1, btn_2, btn_3;

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
        vChoices = view.findViewById(R.id.v_choices);
        btnStart = view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setVisibility(View.GONE);
                vChoices.setVisibility(View.VISIBLE);
                next();
            }
        });

        tvNumber = (TextView) view.findViewById(R.id.tv_number);
        btn_0 = (Button) view.findViewById(R.id.btn_0);
        btn_1 = (Button) view.findViewById(R.id.btn_1);
        btn_2 = (Button) view.findViewById(R.id.btn_2);
        btn_3 = (Button) view.findViewById(R.id.btn_3);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
    }

    private void next() {
        mNumber = mRandom.nextInt(mLevel);
        tvNumber.setText(NumberUtil.getNumberString(mNumber));
        setChoices(mNumber);
    }

    private void setChoices(int number) {
        ArrayList<Integer> choices = new ArrayList<>(4);
        choices.add(number);
        for (int i = 1; i < 4; i++) {
            while (true) {
                int tmp = mRandom.nextInt(mLevel);
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
        btn_0.setText(choices.get(0) + "");
        btn_1.setText(choices.get(1) + "");
        btn_2.setText(choices.get(2) + "");
        btn_3.setText(choices.get(3) + "");
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        if (mNumber == Integer.parseInt(btn.getText().toString())) {
            next();
        }
        else {
            ShakeAnimator.shake(v);
        }
    }
}
