package com.design.classsign.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


/**
 * Created by yuer on 2017/3/13.
 */

public class AnimatUtils {

    private static AnimatorSet animatorSet;

    public static Animation translateAnimatShowTop(View view, int duration) {
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(duration);
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static Animation translateAnimatHideRight(final View view, int duration) {
        view.clearAnimation();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(duration);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static Animation translateAnimatShowRight(final View view, int duration) {
        view.clearAnimation();
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(duration);
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static Animation translateAnimatShowLeft(final View view, int duration) {
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(duration);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static Animation translateAnimatHideLeft(final View view, int duration) {
        view.clearAnimation();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(duration);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static Animation translateAnimatHideTop(final View view) {
        return translateAnimatHideTop(view, 300);
    }


    public static Animation translateAnimatHideTop(final View view, int duraction) {
        view.clearAnimation();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        translateAnimation.setDuration(duraction);
        view.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return translateAnimation;
    }

    public static Animation translateAnimatShowBottom(View view) {
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(translateAnimation);
        return translateAnimation;
    }

    public static void translateAnimatHideBottom(View view) {
        view.clearAnimation();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);
        view.setVisibility(View.GONE);
    }


    public static Animation alphaShow(View view, int duration) {
        view.clearAnimation();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.VISIBLE);
        return alphaAnimation;
    }

    public static Animation alphaHide(View view, int duration) {
        view.clearAnimation();
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.INVISIBLE);
        return alphaAnimation;
    }

    public static Animation scaleShow(View view, int duration) {
        final ScaleAnimation animation = new ScaleAnimation(0.0f, 1f, 0.0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
        return animation;
    }

    public static Animation scaleHide(View view, int duration) {
        final ScaleAnimation animation = new ScaleAnimation(1f, 0.0f, 1.0f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
        return animation;
    }


    public static void rotate(View view, int y, int duration, int startR, int endR) {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(startR, endR, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        ObjectAnimator oaY = ObjectAnimator.ofFloat(view, "rotation", startR, endR);
        oaY.setDuration(duration);
        oaY.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator oaX = ObjectAnimator.ofFloat(view, "rotationX", startR, endR);
        oaX.setDuration(duration);
        oaX.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator tran = translateY(view, y, duration);
        AnimatorSet set = new AnimatorSet();
        set.play(oaY).with(tran);
        set.start();
//        ObjectAnimator oaY = ObjectAnimator.ofFloat(view, "rotation", startR, endR);
//        oaY.setDuration(duration);
//        oaY.setInterpolator(new DecelerateInterpolator());
//        oaY.start();
    }


    public static void alphaRotateHide(View view, int duration) {
        view.clearAnimation();
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(duration);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180);
        rotateAnimation.setDuration(duration);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.start();
        view.setVisibility(View.GONE);
    }

    public static void alphaRotateShow(View view, int direction) {
        view.clearAnimation();
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(direction);
        RotateAnimation rotateAnimation = new RotateAnimation(180, 360);
        rotateAnimation.setDuration(direction);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.start();
        view.setVisibility(View.VISIBLE);
    }

    public static void LadyModelInto(View ladyView, View manView, int direction) {
        ladyView.clearAnimation();
        manView.clearAnimation();
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 70f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        final ScaleAnimation acaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(acaleAnimation);
        animationSet.setDuration(direction);
        alphaAnimation.setDuration(direction);
        animationSet.start();
    }

    public static void ManModelInto(View ladyView, View manView, int direction) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
    }



    /**
     * 从右边消失
     *
     * @param view
     */

    public static void moveToRightHide(View view) {

    }


    /**
     * 从右边显示
     *
     * @param view
     */
    public static void moveToRightShow(View view) {

    }




    public static ObjectAnimator translateY(View view, int y, int duration) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(view, "translationY", y);
        obj.setInterpolator(new DecelerateInterpolator());
        obj.setDuration(duration);
        obj.start();
        return obj;
    }

    public static ObjectAnimator translateX(View view, int x, int duration) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(view, "translationX", x);
        obj.setInterpolator(new DecelerateInterpolator());
        obj.setDuration(duration);
        obj.start();
        return obj;
    }


    public static void moveToLeftShow(View view) {

    }


    /**
     * 指定控件缩放
     *
     * @param view
     * @param duration
     * @param formValue
     * @param toValue
     * @return
     */
    public static Animator scaleSmall(final View view, int duration, float formValue, float toValue) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", formValue, toValue);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", formValue, toValue);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
        return animatorSet;
    }


    public static Animator scaleToHide(final View view, int duration) {
        return scaleSmall(view, duration, 1.0f, 0f);
    }

    public static Animator scaleToShow(View view, int duration) {
        return scaleBig(view, duration, 0f, 1f);
    }

    public static Animator scaleBig(final View view, int duration, float formValue, float toValue) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", formValue, toValue);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", formValue, toValue);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
        return animatorSet;
    }

    /**
     * 开启缩放渐变呼吸动画
     */
    public static void startScaleBreathAnimation(Object ivBreathe) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivBreathe, "scaleX", 0.4f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivBreathe, "scaleY", 0.4f, 1f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(2000);

        animatorSet.setInterpolator(new BreatheInterpolator());
        animatorSet.start();
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
