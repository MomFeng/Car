package com.example.administrator.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画温度计的自定义view
 * Created by MomFeng on 2017/4/23 0023.
 */

public class ThemometerView extends View {
    private Paint mPaint;
    private Paint mPaintline;
    private Paint mPainttext;
    //
    private Paint mPaintback;
    private String[] themometer = {"0","10","20","30","40","50","60","70","80","90"};

    public ThemometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemometerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mPaintline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintline.setColor(Color.BLUE);
        mPaintline.setStrokeWidth(5);

        mPainttext = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPainttext.setColor(Color.BLUE);
        mPainttext.setTextSize(30);

        mPaintback = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintback.setColor(Color.parseColor("#a6947c"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.out.println("getWidth() ----- " + getWidth());
        System.out.println("getHeight() ----- " + getHeight());

        mPaint.setColor(Color.parseColor("#ff0000"));
        canvas.drawCircle(adaptation(210) , adaptation(500) , adaptation(80) , mPaint);
        canvas.drawRect(adaptation(180) , adaptation(20) , adaptation(240) , adaptation(500) , mPaintback);
        canvas.drawRect(adaptation(180) , adaptation(220) , adaptation(240) , adaptation(500) , mPaint);
        canvas.drawText("48" , adaptation(180) + mPainttext.measureText("48")/4 , adaptation(220) - getTxtHeight(mPainttext)/4 , mPainttext);

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(adaptation(110) , adaptation(420-43*i) , adaptation(160) , adaptation(420-43*i) , mPaintline);
            canvas.drawText(themometer[i] , adaptation(110) - mPainttext.measureText(themometer[i]) - adaptation(10) , adaptation(420-43*i) + getTxtHeight(mPainttext)/4 , mPainttext);
        }

        mPainttext.setTextSize(adaptation(40));
        canvas.drawText("水温℃" , adaptation(210) - mPainttext.measureText("水温℃")/2 , adaptation(500) + getTxtHeight(mPainttext)/4 , mPainttext);
    }

    //获取文字的高度
    public float getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    //适配屏幕算法
    public float adaptation(float num){
        float number = 0;
        float multiple  = 300/num;
        number = getWidth()/multiple;
        return number;
    }
}
