package com.design.classsign.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by yzm on 2017/4/25.
 */

public class BreatheAnim {
    private AnimatorSet showBreathAnim;
    private AnimatorSet hideBreathAnim;
    private AnimatorSet breathAnim;
    private ObjectAnimator showAlterAnim;
    private ObjectAnimator hideAlterAnim;
    public static int startCount = 0;
    public static int endCount = 0;
    public static final float translationX = 184f;

    /**
     * 开启缩放渐变呼吸动画
     */
    public void showRepeatBtn(float x, float y, final RelativeLayout rlBreatheLight, final TextView tvBreathBigCircle, final TextView tvBreathSmallCircle, final TextView tvBreathAlert) {
        startCount += 1;
        cancelAnim(rlBreatheLight);
        rlBreatheLight.setVisibility(View.VISIBLE);
        tvBreathAlert.setTranslationX(-translationX);
        rlBreatheLight.setTranslationX(x - 80);
        rlBreatheLight.setTranslationY(y);// - 150);

        if (showBreathAnim == null) {

            ObjectAnimator enterScaleX = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleX", 0.0f, 1.0f);
            ObjectAnimator enterScaleY = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleY", 0.0f, 1.0f);
            ObjectAnimator enterScaleX1 = ObjectAnimator.ofFloat(tvBreathSmallCircle, "scaleX", 0.0f, 1.0f);
            ObjectAnimator enterScaleY1 = ObjectAnimator.ofFloat(tvBreathSmallCircle, "scaleY", 0.0f, 1.0f);
            showBreathAnim = new AnimatorSet();
            showBreathAnim.playTogether(enterScaleX, enterScaleY, enterScaleX1, enterScaleY1);
            showBreathAnim.setDuration(1000);
//            showBreathAnim.setInterpolator(new AccelerateDecelerateInterpolator());
//            showBreathAnim.setInterpolator(new DecelerateInterpolator());
            showBreathAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d("BreatheAnim", "onAnimationStart: ");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d("showRepeatBtn", "onAnimationEnd ");
//                LogUtil.d(TAG, "onAnimationEnd");
                    animBreath(tvBreathBigCircle);
                    animShowAlter(rlBreatheLight, tvBreathBigCircle, tvBreathSmallCircle, tvBreathAlert);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(" showRepeatBtn", "onAnimationCancel ");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        showBreathAnim.start();
    }

    /**
     * 启动呼吸动画
     */
    private void animBreath(final TextView tvBreathBigCircle) {
        if (breathAnim == null) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleX", 0.8f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleY", 0.8f, 1.0f);
            scaleX.setRepeatCount(ValueAnimator.INFINITE);
            scaleY.setRepeatCount(ValueAnimator.INFINITE);
            breathAnim = new AnimatorSet();
            breathAnim.setInterpolator(new AnimatUtils.BreatheInterpolator());
            breathAnim.playTogether(scaleX, scaleY);
            breathAnim.setDuration(2000);

        }
        breathAnim.start();
    }


    /**
     * 显示详情提示
     *
     * @param rlBreatheLight
     * @param tvBreathBigCircle
     * @param tvBreathSmallCircle
     * @param tvBreathAlert
     */
    private void animShowAlter(final RelativeLayout rlBreatheLight, final TextView tvBreathBigCircle, final TextView tvBreathSmallCircle, final TextView tvBreathAlert) {
        if (showAlterAnim == null) {
            showAlterAnim = ObjectAnimator.ofFloat(tvBreathAlert, "translationX", -translationX, 0);
            showAlterAnim.setDuration(800);
            showAlterAnim.setInterpolator(new OvershootInterpolator());
            showAlterAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationRepeat(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(" animShowAlter", "onAnimationCancel ");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(" animShowAlter", "onAnimationEnd ");
//                    animHideAlter(rlBreatheLight, tvBreathBigCircle, tvBreathSmallCircle, tvBreathAlert);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(" animShowAlter", "onAnimationStart ");
                }
            });
        }
        showAlterAnim.start();
    }

    /**
     * 收回详情提示并隐藏呼吸灯
     *
     * @param rlBreatheLight
     * @param tvBreathBigCircle
     * @param tvBreathSmallCircle
     * @param tvBreathAlert
     */
    private void animHideAlter(final RelativeLayout rlBreatheLight, final TextView tvBreathBigCircle, final TextView tvBreathSmallCircle, final TextView tvBreathAlert) {
        if (hideAlterAnim == null) {
            hideAlterAnim = ObjectAnimator.ofFloat(tvBreathAlert, "translationX", 0, -translationX);
            hideAlterAnim.setDuration(1000);
            hideAlterAnim.setStartDelay(6000);
            hideAlterAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(" animHideAlter", "onAnimationStart ");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(" animHideAlter", "onAnimationEnd ");
                    endCount += 1;
                    hideRepeatBtn(rlBreatheLight, tvBreathBigCircle, tvBreathSmallCircle, tvBreathAlert, 800);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(" animHideAlter", "onAnimationCancel ");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }
        hideAlterAnim.start();
    }

    /**
     * 动画隐藏按钮
     * <p>
     * duration 动画执行时间
     */
    public void hideRepeatBtn(final RelativeLayout rlBreatheLight, final TextView tvBreathBigCircle, final TextView tvBreathSmallCircle, final TextView tvBreathAlert, int duration) {
        // 缩小退出
//        if (tvBreathBigCircle.getAnimation() != null) {
//            tvBreathBigCircle.getAnimation().cancel();
//        }

        tvBreathAlert.setTranslationX(-translationX);

        if (hideBreathAnim == null) {
            ObjectAnimator enterScaleX = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleX", 1.0f, 0.0f);
            ObjectAnimator enterScaleY = ObjectAnimator.ofFloat(tvBreathBigCircle, "scaleY", 1.0f, 0.0f);
            ObjectAnimator enterScaleX2 = ObjectAnimator.ofFloat(tvBreathSmallCircle, "scaleX", 1.0f, 0.0f);
            ObjectAnimator enterScaleY2 = ObjectAnimator.ofFloat(tvBreathSmallCircle, "scaleY", 1.0f, 0.0f);

            hideBreathAnim = new AnimatorSet();
            hideBreathAnim.playTogether(enterScaleX, enterScaleY, enterScaleX2, enterScaleY2);
            hideBreathAnim.setInterpolator(new AccelerateInterpolator());
            hideBreathAnim.setDuration(duration);

            hideBreathAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(" hideRepeatBtn", "onAnimationStart ");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(" hideRepeatBtn", "onAnimationEnd  ");
                    if (startCount == endCount) {
                        rlBreatheLight.setVisibility(View.GONE);
                        startCount = 0;
                        endCount = 0;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(" hideRepeatBtn", "onAnimationCancel ");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        hideBreathAnim.start();
    }

    public void cancelAnim() {
        Log.d("BreatheAnim ", "cancelAnim ");
        if (showBreathAnim != null && showBreathAnim.isStarted()) {
            Log.d("BreatheAnim ", "cancelAnim showBreathAnim");
            showBreathAnim.end();
        }
        if (showAlterAnim != null && showAlterAnim.isStarted()) {
            Log.d("BreatheAnim ", "cancelAnim showAlterAnim");
            showAlterAnim.end();
        }
        if (hideBreathAnim != null && hideBreathAnim.isStarted()) {
            Log.d("BreatheAnim ", "cancelAnim hideBreathAnim");
            hideBreathAnim.end();
        }
        if (hideAlterAnim != null && hideAlterAnim.isStarted()) {
            Log.d("BreatheAnim ", "cancelAnim showAlterAnim");
            hideAlterAnim.end();
        }
        if (breathAnim != null && breathAnim.isStarted()) {
            Log.d("BreatheAnim ", "cancelAnim breathAnim");
            breathAnim.end();
        }

    }

    public void cancelAnim(RelativeLayout rlBreatheLight) {
        cancelAnim();
        rlBreatheLight.setVisibility(View.GONE);
    }

    /**
     * 呼吸动画插值器
     */
    static class BreatheInterpolator implements TimeInterpolator {
        @Override
        public float getInterpolation(float input) {
            float x = 6 * input;
            float k = 1.0f / 3;
            int t = 6;
            int n = 1;//控制函数周期，这里取此函数的第一个周期
            float PI = 3.1416f;
            float output = 0;
            if (x >= ((n - 1) * t) && x < ((n - (1 - k)) * t)) {
                output = (float) (0.5 * Math.sin((PI / (k * t)) * ((x - k * t / 2) - (n - 1) * t)) + 0.5);
            } else if (x >= (n - (1 - k)) * t && x < n * t) {
                output = (float) Math.pow((0.5 * Math.sin((PI / ((1 - k) * t)) * ((x - (3 - k) * t / 2) - (n - 1) * t)) + 0.5), 2);
            }
            return output;
        }
    }
}
