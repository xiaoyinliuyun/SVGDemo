package com.yangkunjian.svgdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.ExposedPathParser;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by yangkunjian on 2017/6/27.
 * <p>
 * Svg For Java 最简实现步骤
 * <p>
 * SVG在线编辑器：http://editor.method.ac/ 用于生成pathData路径字符串
 */
public class SvgView extends View {

    public static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();

    private String mPathData;
    private Paint mPaint;
    private Path mPath;
    private float mLength;

    private long mStartTime;//轨迹开始时间点
    private long mTraceTime = 2000;//轨迹动画时间

    public SvgView(Context context) {
        this(context, null);
    }

    public SvgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SvgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }

    private void init() {
        // 1.通过路径字符串生成路径Path
        mPath = ExposedPathParser.createPathFromPathData(mPathData);
        // 2.计算路径长度
        PathMeasure pm = new PathMeasure(mPath, true);
        mLength = pm.getLength();
        // 3.设置开始时间
        mStartTime = System.currentTimeMillis();
        // 4.初始化Paint
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));

    }

    public void setPathData(String pathData) {
        this.mPathData = pathData;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPath == null) return;
        // 1.计算 绘制时间区间
        long t = System.currentTimeMillis() - mStartTime;
        // 2.根据 时间 计算当前相位
        float phase = constrain(0, 1, t * 1f / mTraceTime);
        // 3.根据 相位和总长 计算刷新的距离
        float distance = INTERPOLATOR.getInterpolation(phase) * mLength;
        // 4.设置 Paint 本次绘制效果

        mPaint.setPathEffect(new DashPathEffect(new float[]{distance, mLength}, 0));
        // 5.绘图
        canvas.drawPath(mPath, mPaint);
        // 6.判断是否完成，如果没有，继续刷新
        if (t < mTraceTime)
            ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mStartTime = System.currentTimeMillis();

        ViewCompat.postInvalidateOnAnimation(this);
        return super.onTouchEvent(event);
    }
}
