package com.yangkunjian.svgdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yangkunjian.svgdemo.R;

/**
 * Created by yangkunjian on 2017/10/10.
 */

public class AnimatorDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        imageView = (ImageView) findViewById(R.id.iv_property);
        imageView.setOnClickListener(this);
        // api 3.0之前这些个属性是不存在的
        // imageView.setRotationX(rotationX);
        // imageView.setAlpha(alpha);
        // imageView.setTranslationX(translationX);
    }

    @Override
    public void onClick(View v) {

        // 第一个案例：旋转
        // target表示作用目标
        // propertyName表示属性名称(rotationX:旋转)
        // values:表示从哪里旋转到哪里
        ObjectAnimator oAnimator = ObjectAnimator.ofFloat(v, "rotationY", 0.0f, 360.0f).setDuration(2000);
        oAnimator.start();

        // 第二个案例（监听动画执行过程）
        // 注：虽然我们输入的属性不存在，但是仍然会执行（指的是时间）
        // 非主流用法
        ObjectAnimator objAnimator = ObjectAnimator.ofFloat(v, "dream", 0.0f, 50.0f);
        objAnimator.setDuration(200);
        objAnimator.start();
        // 添加动画监听(监听动画执行过程)
        objAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // getAnimatedValue：指的是当前时间在某一个时刻对应的value值 （距离/时间＝速度）
                float animatedValue = (float) animation.getAnimatedValue();
                // imageView.setScaleX(Math.max(0.8f, animatedValue));
                // imageView.setScaleY(Math.max(0.8f, animatedValue));
                imageView.setTranslationX(animatedValue);
                // imageView.setAlpha(alpha);
            }
        });

        // 第三个案例：多个动画同时执行
        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f);
        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXholder, scaleYholder).setDuration(2000);
        objectAnimator.start();

        // // 第四个案例－－－－－－ValueAnimator－－－－－－－
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 100.0f).setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                // imageView.setScaleX(animatedValue);
                // imageView.setScaleY(animatedValue);
                imageView.setTranslationX(animatedValue);
            }
        });

        // 第五个案例－－－实现多个动画同时执行，实现抛物线效果----估值器

        // 总时间
        final int duration = 2000;
        ValueAnimator valueAnimator = new ValueAnimator().setDuration(duration);
        valueAnimator.setObjectValues(new PointF(0, 0));

        // 估值器：可以用来计算我们的view在屏幕当中显示的位置（运动的轨迹：平移、缩放、抛物线等等......）
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                // fraction：表示百分比，代表当前执行到了什么程度
                // duration:总时间
                // t表示当前时间
                // 百分比： fraction = t/duration
                // 时间：t ＝ fraction*duration
                // 速度我们自定义：自已规定速度s ＝ 50px/s
                // x = t * s
                // pointF.x: 表示距离
                // 创建一个点
                PointF pointF = new PointF();
                // 计算当前实现
                float t = fraction * duration / 1000;
                // 速度＝50px/s
                int s = 100;
                // 计算当前执行的距离
                pointF.x = t * s;
                // pointF.y : y = 1/2*g*t*t;
                pointF.y = 1 / 2.0f * 98f * t * t;

                return pointF;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

        });

        valueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f);
                PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXholder, scaleYholder);
                objectAnimator.setDuration(2000);
                objectAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

        // 第六个案例－－－AnimatorSet动画集合
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 2.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 2.0f);
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 0.0f, 100.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        // animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        // animatorSet.start();
        // 补充知识:思考当我们的缩放动画执行完毕的时候执行我们的平移动画？
        // with：同时执行 after：在谁的后面执行 before：在谁的前面执行
        animatorSet.play(scaleXAnimator).with(scaleYAnimator).with(translationXAnimator);
        // animatorSet.play(scaleXAnimator).with(scaleYAnimator);
        // animatorSet.play(scaleXAnimator).after(translationXAnimator);
        animatorSet.start();

        // 第七个案例－－－使用xml文件配置动画
        Animator loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.animator_set_x_and_y);
        loadAnimator.setTarget(imageView);
        loadAnimator.start();

    }

    class DreamPoint {
        private boolean bool;
    }
}
