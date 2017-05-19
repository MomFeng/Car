package com.hncst.administrator.car.view;

/**
 * 画在仪表显示界面里面直接用来显示的折线图
 * Created by MomFeng on 2017/4/22 0022.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class LinechartView extends View{
    private int XPoint = 170;
    private int YPoint = 740;
    private int XScale = 25; // 刻度长度
    private int YScale = 50;
    private int XLength = 875;
    private int YLength = 250;

    private Paint mPaintline;
    private Paint mPainttext;

    private int MaxDataSize = XLength / XScale;

    private List<Integer> data = new ArrayList<Integer>();

    private String[] YLabel = new String[YLength / YScale];

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1234) {
                LinechartView.this.postInvalidate();
            }
        };
    };

    public LinechartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < YLabel.length*10; i+=10) {
            YLabel[0] = (1000) + "";
            YLabel[1] = (2000) + "";
            YLabel[2] = (3000) + "";
            YLabel[3] = (4000) + "";
            YLabel[4] = (5000) + "";
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data.size() >= MaxDataSize) {
                        data.remove(0);
                    }
                    data.add(new Random().nextInt(4000) + 1000);
                    handler.sendEmptyMessage(0x1234);
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 1080;
        int height = 350;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else {
            height = (int) (width / 3.0);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(adaptation(30));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.BLUE);

        mPaintline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintline.setStrokeWidth(adaptation(5));
        mPaintline.setStyle(Paint.Style.STROKE);
        mPaintline.setAntiAlias(true);
        mPaintline.setColor(Color.BLUE);

        mPainttext = new Paint();
        mPainttext.setTextSize(adaptation(60));
        mPainttext.setStyle(Paint.Style.STROKE);
        mPainttext.setAntiAlias(true); // 去锯齿
        mPainttext.setColor(Color.BLUE);

        // 画Y轴
        canvas.drawLine(adaptation(XPoint), adaptation(20), adaptation(XPoint), adaptation(270), mPaintline);

        // Y轴箭头
        canvas.drawLine(adaptation(XPoint), adaptation(20), adaptation(XPoint - 9), adaptation(20 + 18), mPaintline); // 箭头
        canvas.drawLine(adaptation(XPoint), adaptation(20), adaptation(XPoint + 9), adaptation(20 + 18), mPaintline);

        // 添加刻度和文字
        for (int i = 0; i * YScale < YLength; i++) {
            canvas.drawLine(adaptation(XPoint), adaptation(270 - i * YScale), adaptation(XPoint + 14), adaptation(270 - i
                    * YScale), mPaintline); // 刻度

            canvas.drawText(YLabel[i], adaptation(XPoint - 85), adaptation(270 - i * YScale + 10), paint);// 文字
        }

        // 画X轴
        canvas.drawLine(adaptation(XPoint), adaptation(270), adaptation(XPoint + XLength), adaptation(270), mPaintline);
        canvas.drawText("当前发动机转速R/Min" , getWidth()/2 - mPainttext.measureText("当前发动机转速R/Min")/2, adaptation(330) , mPainttext);

        // 绘折线
        /*
         * if(data.size() > 1){ for(int i=1; i<data.size(); i++){
         * canvas.drawLine(XPoint + (i-1) * XScale, YPoint - data.get(i-1) *
         * YScale, XPoint + i * XScale, YPoint - data.get(i) * YScale, paint); }
         * }
         */
        paint.setStyle(Paint.Style.FILL);
        if (data.size() > 1) {
            Path path = new Path();
            path.moveTo(adaptation(XPoint), adaptation(270));
            for (int i = 0; i < data.size(); i++) {
                path.lineTo(adaptation(XPoint + i * XScale), adaptation(270 - ((float)data.get(i)/1000) * YScale));
            }
            path.lineTo(adaptation(XPoint + (data.size() - 1) * XScale), adaptation(270));
            canvas.drawPath(path, paint);
        }
        postInvalidate();
    }

    //适配屏幕算法
    public float adaptation(float num){
        float number = 0;
        float multiple  = 1080/num;
        number = getWidth()/multiple;
        return number;
    }
}