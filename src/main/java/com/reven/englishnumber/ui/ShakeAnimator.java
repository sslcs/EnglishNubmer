package com.reven.englishnumber.ui;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by CS on 2015/5/15.
 * Shake Animator
 */
public class ShakeAnimator {
    public static void shake(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -10, 10, -10, 10, -10, 10, 0);
        animator.start();
    }

    public static void shake(View view, AnimatorListenerAdapter listenerAdapter) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -10, 10, -10, 10, -10, 10, 0);
        animator.addListener(listenerAdapter);
        animator.start();
    }
}
