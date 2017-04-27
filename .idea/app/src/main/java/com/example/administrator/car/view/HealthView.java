package com.example.administrator.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class HealthView extends View {

    private Paint mPaint_4fc0ef;//4fc0ef的画笔
    private Paint mPaint_4fc0ef_fill;
    private Paint mPaint_4af;//4af的笔
    private Paint mPaint_adf9fd;//bbb的笔
    private Paint mPaint_white;//白色的笔
    private Paint mPaint_white_text;//写字的白色的笔
    private Paint mPaint_bigwhite_text;//写巨大字的白色的笔
    private Paint mPaint_middlewhite_text;//写中号字的白色的笔
    private Paint mPaint_smallwhite_text;//写小号字的白色的笔
    private float mHealth;//健康的数值
    private int mHealth_int;
    private String[] number = {"0","10","20","30","40","50","60","70","80","90","100"};

    public HealthView(Context context) {
        super(context);
        init();
    }

    public HealthView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public HealthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mPaint_smallwhite_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_smallwhite_text.setColor(Color.WHITE);
        mPaint_smallwhite_text.setAntiAlias(true);

        mPaint_middlewhite_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_middlewhite_text.setColor(Color.WHITE);
        mPaint_middlewhite_text.setAntiAlias(true);

        mPaint_bigwhite_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_bigwhite_text.setColor(Color.WHITE);
        mPaint_bigwhite_text.setAntiAlias(true);

        mPaint_white_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_white_text.setColor(Color.WHITE);
        mPaint_white_text.setAntiAlias(true);

        mPaint_white = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_white.setColor(Color.WHITE);
        mPaint_white.setAntiAlias(true);
        mPaint_white.setStyle(Paint.Style.STROKE);

        mPaint_4fc0ef = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_4fc0ef.setColor(Color.parseColor("#4fc0ef"));
        mPaint_4fc0ef.setAntiAlias(true);
        mPaint_4fc0ef.setStyle(Paint.Style.STROKE);

        mPaint_4fc0ef_fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_4fc0ef_fill.setColor(Color.parseColor("#4fc0ef"));
        mPaint_4fc0ef_fill.setAntiAlias(true);

        mPaint_adf9fd = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_adf9fd.setColor(Color.parseColor("#ddf9fd"));
        mPaint_adf9fd.setAntiAlias(true);
        mPaint_adf9fd.setStyle(Paint.Style.STROKE);

        mPaint_4af = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_4af.setColor(Color.parseColor("#44aaff"));
        mPaint_4af.setAntiAlias(true);
        mPaint_4af.setStyle(Paint.Style.FILL);
    }

    //用来控制视图的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 1080;
        int height = 750;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else {
            height = (int) (width / 1.44);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint_4af);
        //100,30,1080*2,750
        //1080/10.8
        //1080-170=910
        //100,30,980,910
        //1080/30
        //画弧线的1080/10.8
        //1920*1080----1280*720
        mPaint_4fc0ef.setStrokeWidth(adaptation(15));
        mPaint_adf9fd.setStrokeWidth(adaptation(15));
        RectF oval=new RectF(adaptation(100),adaptation(20),adaptation(980),adaptation(900));
        //浅色的弧线
        canvas.drawArc(oval, 170, 200, false, mPaint_4fc0ef);
        //深色的弧线
        canvas.drawArc(oval, 170, mHealth, false, mPaint_adf9fd);
        //180,100,900,820
        //720，777，202
        //内圈大浅色弧线线
        mPaint_4fc0ef_fill.setStrokeWidth(adaptation(70));
        mPaint_4fc0ef_fill.setStyle(Paint.Style.STROKE);
        RectF oval1=new RectF(adaptation(180),adaptation(100),adaptation(900),adaptation(820));
        canvas.drawArc(oval1, 170, 200, false, mPaint_4fc0ef_fill);

        mPaint_white.setStrokeWidth(getWidth()/216);
        mPaint_white_text.setTextSize(getWidth()/27);
        canvas.drawLine(getWidth()/2 , adaptation(65), getWidth()/2 , adaptation(135) ,mPaint_white);
        canvas.drawText(number[5] , getWidth()/2-mPaint_white_text.measureText(number[5])/2 , adaptation(180) , mPaint_white_text);
        //每边是200/2 = 100度
        //旋转画布，画右边的刻度
        for (int i = 6; i <= 10; i++) {
            float width = mPaint_white_text.measureText(number[i])/2;
            canvas.rotate(20,getWidth()/ 2,adaptation(460));
            canvas.drawLine(getWidth()/2 , adaptation(65) , getWidth()/2 , adaptation(135), mPaint_white);
            canvas.drawText(number[i] , getWidth()/2-width , adaptation(180) , mPaint_white_text);
        }
        canvas.rotate(-100,getWidth()/ 2,adaptation(460));

        //旋转画布，画左边的刻度
        for (int i = 4; i >=0; i--) {
            float width = mPaint_white_text.measureText(number[i])/2;
            canvas.rotate(-20,getWidth()/ 2,adaptation(460));
            canvas.drawLine(getWidth()/2 , adaptation(65) , getWidth()/2 , adaptation(135), mPaint_white);
            canvas.drawText(number[i] , getWidth()/2-width , adaptation(180) , mPaint_white_text);
        }
        canvas.rotate(100,getWidth()/ 2,adaptation(460));

        mPaint_bigwhite_text.setTextSize(adaptation(250));
        canvas.drawText(mHealth_int+"",getWidth()/2 -mPaint_bigwhite_text.measureText(mHealth_int+"") / 2, adaptation(430) , mPaint_bigwhite_text);

        //车辆健康指数
        mPaint_middlewhite_text.setTextSize(adaptation(40));
        canvas.drawText("车辆健康指数" , getWidth()/2 -mPaint_middlewhite_text.measureText("车辆健康指数") / 2, adaptation(340)+getTxtHeight(mPaint_bigwhite_text) / 2 , mPaint_middlewhite_text);

        //体检时间:2017-04-15
        mPaint_smallwhite_text.setTextSize(adaptation(32));
        canvas.drawText("体检时间:2017-04-15" , getWidth()/2 -mPaint_smallwhite_text.measureText("体检时间:2017-04-15") / 2, adaptation(370)+getTxtHeight(mPaint_bigwhite_text) / 2+getTxtHeight(mPaint_smallwhite_text) / 2 , mPaint_smallwhite_text);

        canvas.drawText("0.00" , adaptation(230)-mPaint_middlewhite_text.measureText("0.00") / 2 , adaptation(640) , mPaint_middlewhite_text);
        canvas.drawText("车速KM/H" , adaptation(230)-mPaint_smallwhite_text.measureText("车速KM/H") / 2 , adaptation(680) , mPaint_smallwhite_text);

        canvas.drawLine(adaptation(380) , adaptation(600), adaptation(380) , adaptation(690) , mPaint_white);

        canvas.drawText("5.06" ,  adaptation(525)-mPaint_middlewhite_text.measureText("5.06") / 2 , adaptation(640) , mPaint_middlewhite_text);
        canvas.drawText("今日耗油L" , adaptation(525)-mPaint_smallwhite_text.measureText("今日耗油L") / 2 , adaptation(680) , mPaint_smallwhite_text);

        canvas.drawLine(adaptation(670) , adaptation(600) ,adaptation(670) ,adaptation(690) , mPaint_white);

        canvas.drawText("159.6" ,  adaptation(820)-mPaint_middlewhite_text.measureText("159.6") / 2 , adaptation(640) , mPaint_middlewhite_text);
        canvas.drawText("今日里程KM" , adaptation(820)-mPaint_smallwhite_text.measureText("今日里程KM") / 2 , adaptation(680) , mPaint_smallwhite_text);

        postInvalidate();

        super.onDraw(canvas);
    }

    public void setmHealth(float mHealth){
        this.mHealth = mHealth;
    }

    public void setmHealth_int(int mHealth_int){
        this.mHealth_int = mHealth_int;
    }


    //获取文字的高度
    public float getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    //适配屏幕算法
    public float adaptation(float num){
        float number = 0;
        float multiple  = 1080/num;
        number = getWidth()/multiple;
        return number;
    }
}