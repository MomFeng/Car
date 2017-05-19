package com.hncst.administrator.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.R.attr.defaultValue;

/**
 * 画用于展示平均耗油的自定义View
 * Created by MomFeng on 2017/4/23 0023.
 */

public class MyCircleView extends View{

    private Paint linePaint;
    private Paint textPaint;
    private Paint centerTextPaint;
    private Paint circlePaint;
    private Paint indicatorPaint;

    private SweepGradient mSweepGradient;

    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        // 默认宽高;

        switch (mode) {
            case MeasureSpec.AT_MOST:
                // 最大值模式 当控件的layout_Width或layout_height属性指定为wrap_content时
                size = Math.min(defaultValue, size);
                break;
            case MeasureSpec.EXACTLY:
                // 精确值模式
                // 当控件的android:layout_width=”100dp”或android:layout_height=”match_parent”时

                break;
            default:
                size = defaultValue;
                break;
        }
        return size;
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setColor(Color.CYAN);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(1.0f);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);

        centerTextPaint = new Paint();
        centerTextPaint.setColor(Color.BLUE);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setTextSize(80);

        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);//实现末端圆弧
        circlePaint.setStrokeWidth(60.0f);

        indicatorPaint=new Paint();
        indicatorPaint.setColor(0xFFF7F709);
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Paint.Style.FILL);

        // 着色的共有270度，这里设置了12个颜色均分360度s
        int[] colors = { 0xFFD52B2B, 0xFFf70101, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFF6AE2FD, 0xFF8CD0E5, 0xFFA3CBCB, 0xFFD1C299, 0xFFE5BD7D,
                0xFFAA5555, 0xFFBB4444, 0xFFC43C3C };

        // 渐变色
        //mSweepGradient = new SweepGradient(mCenter, mCenter, colors, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
