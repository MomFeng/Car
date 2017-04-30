package com.example.administrator.car;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 用Surfaceview画图的一个图自定义视图
 * Created by MomFeng on 2017/4/12 0012.
 */

public class CarView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    /**
     * 用于绘制的线程
     */
    private Thread t;

    /**
     * 线程的控制开关
     */
    private boolean isRunning;

    private String[] mSpeed = new String[]{"0","20","40","60","80","100","120","140","160","180","200","220","240"};

    //测试用的车速
    private float msp = 0.0f;

    //当前的车速
    private int mCarspeed = 0;
    //控制指针旋转角度的变量
    private int mRadius = 60;
    //渐变渲染
    Shader mSweepGradient = null;
    //适配屏幕的一个倍数(默认为1920*1080)
    private float number = 1;

    private Paint mPaint_blue;//蓝色的画笔
    private Paint mPaint_white;//白色的画笔
    private Paint mPaint_green;//绿色的画笔
    private Paint mPaint_red;//红色的画笔
    private Paint mPaint_blue_line;//画刻度线的笔
    private Paint mPaint_red_line;//画指示器指针的笔
    private Paint mPaint_blue_text;//写刻度的笔
    private Paint mPaint_blue_d2e9ff;//淡蓝色的笔
    private Paint mPaint_color;//梯度渲染的画笔

    //privatee

    public CarView(Context context) {
        super(context, null);
        init();
    }

    public CarView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public CarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mHolder = getHolder();
        mHolder.addCallback(this);

        //可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常量
        setKeepScreenOn(true);
    }


    //测量屏幕宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //创建
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSweepGradient = new SweepGradient(getWidth()/2, getHeight()/2, new int[] {0x33FF0000 ,0xFF00FF00 , 0x33FF2222 }, null);

        mPaint_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_color.setStyle(Paint.Style.FILL);
        mPaint_color.setShader(mSweepGradient);

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
        mPaint_blue_line.setStrokeWidth(5);
        mPaint_blue_line.setColor(Color.BLUE);

        mPaint_blue_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue_text.setStrokeWidth(2);
        mPaint_blue_text.setColor(Color.BLUE);
        mPaint_blue_text.setTextSize(40);

        mPaint_red_line = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_red_line.setStrokeWidth(10);
        mPaint_red_line.setColor(Color.RED);

        mPaint_blue_d2e9ff = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_blue_d2e9ff.setStyle(Paint.Style.FILL);
        mPaint_blue_d2e9ff.setColor(Color.parseColor("#d2e9ff"));

        isRunning = true;
        t = new Thread(this);
        t.start();

    }

    //更改
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //销毁
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {

        while(isRunning){
            draw();

            /*try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    //自定义画画的方法
    private void draw() {

        mCanvas = mHolder.lockCanvas();
        try {
            if(mCanvas != null){
                //msp +=0.5;
                //Thread.sleep(2);

                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                //1080/2=540
                //540*0.83=450

                //450
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)(getWidth()/2*0.833),mPaint_blue);
                //440
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)(getWidth()/2*0.833-10*0.833),mPaint_white);
                //440
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)(getWidth()/2*0.833-10*0.833),mPaint_color);
                //340
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)(getWidth()/2*0.833-110*0.833),mPaint_white);
                //460
                RectF oval=new RectF(getWidth()/2-(int)(getWidth()/2*0.833+10*0.83),getHeight()/2-(int)(getWidth()/2*0.833+10*0.83),getWidth()/2+(int)(getWidth()/2*0.833+10*0.83),getHeight()/2+(int)(getWidth()/2*0.833+10*0.83));

                mCanvas.drawArc(oval, 30, 120, true, mPaint_white);
                //440
                RectF oval1=new RectF(getWidth()/2-(int)(getWidth()/2*0.833-10*0.83),getHeight()/2-(int)(getWidth()/2*0.833-10*0.83),getWidth()/2+(int)(getWidth()/2*0.833-10*0.83),getHeight()/2+(int)(getWidth()/2*0.833-10*0.83));
                mCanvas.drawArc(oval1, 150, mCarspeed+1, true, mPaint_color);

                //45
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)((getWidth()/2*0.833)/10),mPaint_blue);
                //40
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)((getWidth()/2*0.833)/10-5*0.833),mPaint_white);
                //30
                mCanvas.drawCircle(getWidth()/2,getHeight()/2,(int)((getWidth()/2*0.833)/10-15*0.833),mPaint_red);

                //450                                                                     400
                mCanvas.drawLine(getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833),getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833-50*0.833),mPaint_blue_line);

                float width_6 = mPaint_blue_text.measureText(mSpeed[6])/2;
                mCanvas.drawText(mSpeed[6],getWidth()/2-width_6,getHeight()/2-(int)(getWidth()/2*0.833+15*0.833),mPaint_blue_text);

                for(int i = 7;i < 13;i++) {
                    float width = mPaint_blue_text.measureText(mSpeed[i])/2;
                    mCanvas.rotate(20,getWidth()/ 2,getHeight() / 2);
                    mCanvas.drawLine(getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833),getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833-50*0.833), mPaint_blue_line);
                    mCanvas.drawText(mSpeed[i],getWidth()/2-width,getHeight()/2-(int)(getWidth()/2*0.833+15*0.833),mPaint_blue_text);
                }
                mCanvas.rotate(-120,getWidth()/ 2,getHeight() / 2);

                for(int i = 5;i >= 0;i--) {
                    float width = mPaint_blue_text.measureText(mSpeed[i])/2;
                    mCanvas.rotate(-20,getWidth()/ 2,getHeight() / 2);
                    mCanvas.drawLine(getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833),getWidth()/2-3,getHeight()/2-(int)(getWidth()/2*0.833-50*0.833), mPaint_blue_line);
                    mCanvas.drawText(mSpeed[i],getWidth()/2-width,getHeight()/2-(int)(getWidth()/2*0.833+15*0.833),mPaint_blue_text);
                }
                mCanvas.rotate(120,getWidth()/ 2,getHeight() / 2);

                float width = mPaint_blue_text.measureText("KM/H")/2;
                mCanvas.drawText("KM/H",getWidth()/2-width,getHeight()/2-(int)(getWidth()/2*0.833-250*0.833),mPaint_blue_text);
                //440
                mCanvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2-(float)(((int)(getWidth()/2*0.833)*Math.sin(Math.PI*(mRadius+mCarspeed)/180))),getHeight()/2+(float)(((int)(getWidth()/2*0.833)*Math.cos(Math.PI*(mRadius+mCarspeed)/180))),mPaint_red_line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    /**
     * 对外提供一个传入当前车速的方法
     */
    public void setSpeed(int mCarspeed){
        this.mCarspeed   = mCarspeed;
    }
}
