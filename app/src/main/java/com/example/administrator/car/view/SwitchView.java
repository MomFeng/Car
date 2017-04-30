package com.example.administrator.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 画仿IOS开关的自定义View
 * Created by MomFeng on 2017/4/22 0022.
 */
public class SwitchView extends View {

    private final Paint paint = new Paint();
    private final Path sPath = new Path();

    private int mWidth, mHeight;
    private float sWidth, sHeight;
    private float sLeft, sTop, sRight, sBottom;
    private float sCenterX, sCenterY;
    private float sAnim;
    private boolean isOn;
    private float bRadius, bStrokWidth;
    private float bWidth;
    private float bLeft, bTop, bRight, bBottom;
    private float sScaleCenterX;
    private float sScale;
    private float bTranslateX;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (widthSize * 0.65f);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w; // 视图自身宽度
        mHeight = h; // 视图自身高度
        sLeft = sTop = 0; // 田径场 左和上的坐标
        sRight = mWidth; // 田径场 右占自身的全部
        sBottom = mHeight * 0.9f; // 田径场底部 占全身的百分之八十.
        sWidth = sRight - sLeft; // 田径场的宽度
        sHeight = sBottom - sTop; // 田径场的高度
        sCenterX = (sRight + sLeft) / 2; // 田径场的X轴中心坐标
        sCenterY = (sBottom + sTop) / 2; // 田径场的Y轴中心坐标

        RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
        sPath.arcTo(sRectF, 90, 180);
        sRectF.left = sRight - sBottom;
        sRectF.right = sRight;
        sPath.arcTo(sRectF, 270, 180);
        sPath.close();    // path准备田径场的路径

        bLeft = bTop = 0;
        bRight = bBottom = sBottom; // 和田径场同高，同宽的节奏， 没错包裹圆形的肯定是个正方形是小孩子都知道的。
        bWidth = bRight - bLeft;
        final float halfHeightOfS = (sBottom - sTop) / 2;
        bRadius = halfHeightOfS * 0.9f; // 按钮的半径
        bStrokWidth = 2 * (halfHeightOfS - bRadius); // 按钮的边框

        sScale = 1 - bStrokWidth / sHeight; //替换之前的0.98<
        sScaleCenterX = sWidth - halfHeightOfS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.parseColor("#0000ff"));
        //paint.setColor(0xffcccccc);
        canvas.drawPath(sPath, paint); // 画出田径场

        sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0; // 动画标示 ，重绘10次
        // draw logic - 动态改变绘制参数，达到动画效果
        paint.reset();
        if (sAnim > 0) invalidate(); // 继续重绘

        sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0; // 动画标示 ，重绘10次

        final float scale = 0.98f * (isOn ? sAnim : 1 - sAnim); //缩放大小参数随sAnim变化而变化
        canvas.save();
        canvas.scale(scale, scale, sCenterX, sCenterY);
        paint.setColor(0xffcccccc);
        canvas.drawPath(sPath, paint);
        canvas.restore();

        bTranslateX = sWidth - bWidth;
        final float translate = bTranslateX * (isOn ? 1 - sAnim : sAnim); // 平移距离参数随sAnim变化而变化
        canvas.translate(translate, 0);

        paint.reset();
        if (sAnim > 0)
            invalidate(); // 继续重绘

        canvas.save();
        paint.setStyle(Style.FILL);
        paint.setColor(0xff44aaff);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮白底
        paint.setStyle(Style.STROKE);
        //paint.setColor(0xffdddddd);
        paint.setStrokeWidth(bStrokWidth);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮灰边  //已修改成白边
        canvas.restore();
    }

    //对外开放可以调用修改当前状态的方法
    public void setcheck(boolean isOn) {
        this.isOn = isOn;
        postInvalidate();
    }

    public boolean getcheck() {
        return this.isOn;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                sAnim = 1; // 动画标示
                isOn = !isOn; // 状态标示 ，开关
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
