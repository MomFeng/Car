package com.hncst.administrator.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 画用于展示速度的仪表盘
 * Created by MomFeng on 2017/4/12 0012.
 */

public class MycarView extends View{

    private String[] mSpeed = new String[]{"0","20","40","60","80","100","120","140","160","180","200","220","240"};

    //当前的车速
    private int mCarspeed = 0;
    //控制指针旋转角度的变量
    private int mRadius = 60;
    //渐变渲染
    Shader mSweepGradient = null;

    private Paint mPaint_blue;//蓝色的画笔
    private Paint mPaint_white;//白色的画笔
    private Paint mPaint_green;//绿色的画笔
    private Paint mPaint_red;//红色的画笔
    private Paint mPaint_4af;//画背景的笔
    private Paint mPaint_blue_line;//画刻度线的笔
    private Paint mPaint_red_line;//画指示器指针的笔
    private Paint mPaint_blue_text;//写刻度的笔
    private Paint mPaint_blue_d2e9ff;//淡蓝色的笔
    private Paint mPaint_color;//梯度渲染的画笔

    public MycarView(Context context) {
        super(context);
        init();
    }

    public MycarView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MycarView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mPaint_4af = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_4af.setStyle(Paint.Style.FILL);
        mPaint_4af.setColor(Color.parseColor("#44aaff"));

        mPaint_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_color.setStyle(Paint.Style.FILL);

        mPaint_blue = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue.setStyle(Paint.Style.FILL);
        mPaint_blue.setColor(Color.BLUE);

        mPaint_white = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_white.setStyle(Paint.Style.FILL);
        mPaint_white.setColor(Color.WHITE);

        mPaint_green = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_green.setStyle(Paint.Style.FILL);
        mPaint_green.setColor(Color.GREEN);

        mPaint_red = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_red.setStyle(Paint.Style.FILL);
        mPaint_red.setColor(Color.RED);

        mPaint_blue_line = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue_line.setColor(Color.BLUE);

        mPaint_blue_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue_text.setStrokeWidth(2);
        mPaint_blue_text.setColor(Color.BLUE);


        mPaint_red_line = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_red_line.setColor(Color.RED);

        mPaint_blue_d2e9ff = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue_d2e9ff.setStyle(Paint.Style.FILL);
        mPaint_blue_d2e9ff.setColor(Color.parseColor("#d2e9ff"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);

        mSweepGradient = new SweepGradient(getWidth()/2, getHeight()/2, new int[] {0x33FF0000 ,0xFF00FF00 , 0x33FF2222}, null);
        mPaint_color.setShader(mSweepGradient);

        mPaint_blue_text.setTextSize(adaptation(65));
        mPaint_blue_line.setStrokeWidth(adaptation(5));
        mPaint_red_line.setStrokeWidth(adaptation(10));

        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(450),mPaint_blue);
        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(440),mPaint_white);

        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(440),mPaint_color);
        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(340),mPaint_white);

        RectF oval=new RectF(getWidth()/2-adaptation(460),getHeight()/2-adaptation(460),getWidth()/2+adaptation(460),getHeight()/2+adaptation(460));

        mCanvas.drawArc(oval, 30, 120, true, mPaint_white);
        RectF oval1=new RectF(getWidth()/2-adaptation(440),getHeight()/2-adaptation(440),getWidth()/2+adaptation(440),getHeight()/2+adaptation(440));
        mCanvas.drawArc(oval1, 150, mCarspeed+1, true, mPaint_color);

        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(45),mPaint_blue);
        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(40),mPaint_white);
        mCanvas.drawCircle(getWidth()/2,getHeight()/2,adaptation(30),mPaint_red);

        mCanvas.drawLine(getWidth()/2,getHeight()/2-adaptation(450),getWidth()/2,getHeight()/2-adaptation(400),mPaint_blue_line);

        mCanvas.drawText(mSpeed[6],getWidth()/2-mPaint_blue_text.measureText(mSpeed[6])/2 ,getHeight()/2-adaptation(465),mPaint_blue_text);

        for(int i = 7;i < 13;i++) {
            mCanvas.rotate(20,getWidth()/ 2,getHeight() / 2);
            mCanvas.drawLine(getWidth()/2,getHeight()/2-adaptation(450),getWidth()/2,getHeight()/2-adaptation(400), mPaint_blue_line);
            mCanvas.drawText(mSpeed[i],getWidth()/2-mPaint_blue_text.measureText(mSpeed[i])/2,getHeight()/2-adaptation(465),mPaint_blue_text);
        }
        mCanvas.rotate(-120,getWidth()/ 2,getHeight() / 2);

        for(int i = 5;i >= 0;i--) {
            mCanvas.rotate(-20,getWidth()/ 2,getHeight() / 2);
            mCanvas.drawLine(getWidth()/2,getHeight()/2-adaptation(450),getWidth()/2,getHeight()/2-adaptation(400), mPaint_blue_line);
            mCanvas.drawText(mSpeed[i],getWidth()/2-mPaint_blue_text.measureText(mSpeed[i])/2,getHeight()/2-adaptation(465),mPaint_blue_text);
        }
        mCanvas.rotate(120,getWidth()/ 2,getHeight() / 2);

        mCanvas.drawText("KM/H",getWidth()/2-mPaint_blue_text.measureText("KM/H")/2,getHeight()/2-getHeight()/6,mPaint_blue_text);
        mCanvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2-adaptation((int)((450*Math.sin(Math.PI*(mRadius+mCarspeed)/180)))),getHeight()/2+adaptation((int)((450*Math.cos(Math.PI*(mRadius+mCarspeed)/180)))),mPaint_red_line);
        mPaint_blue_text.setTextSize(adaptation(120));
        mCanvas.drawText("实时车速" , getWidth()/2-mPaint_blue_text.measureText("实时车速")/2 , getHeight()-getHeight()/6 , mPaint_blue_text);

        postInvalidate();
    }

    /**
     * 适配屏幕算法
     */
    public float adaptation(float num){
        float number = 0;
        float multiple  = 1080/num;
        number = getWidth()/multiple;
        return number;
    }

    /**
     * 对外提供一个传入当前车速的方法
     */
    public void setSpeed(int mCarspeed){
        this.mCarspeed = mCarspeed;
    }
}
