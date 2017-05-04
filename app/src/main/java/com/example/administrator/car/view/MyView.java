package com.example.administrator.car.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.car.R;

/**
 * 画APP主界面下方导航条渐变效果的自定义View
 * Created by MomFeng on 2017/4/20 0020.
 */

public class MyView extends View {

    private String mText;
    private float mTextSize;
    private Bitmap mBottomBitmap;
    private Bitmap mTopBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private Paint mTextPaint;
    private Rect mTextBounds;
    private Paint mBottomBitmapPaint;
    private Paint mTopBitmapPaint;
    private Rect mBitmapRect;
    private int xText;
    private int yText;
    private int mAlpha;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context mContext;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //从xml定义属性中拿到对应数值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.MyView_text:
                    mText = ta.getString(index);
                    break;
                case R.styleable.MyView_textsize:
                    mTextSize = ta.getDimension(index, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyView_bottomBitmap:
                    BitmapDrawable bottomDrawable = (BitmapDrawable) ta.getDrawable(index);
                    mBottomBitmap = bottomDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                    break;
                case R.styleable.MyView_topBitmap:
                    BitmapDrawable topDrawable = (BitmapDrawable) ta.getDrawable(index);
                    mTopBitmap = topDrawable.getBitmap();
                    break;
            }
        }
        //拿到底图的宽高
        mBitmapWidth = mBottomBitmap.getWidth();
        mBitmapHeight = mBottomBitmap.getHeight();
        //初始化字体画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextBounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        mTextPaint.setTextSize(mTextSize);
        //初始化底图和覆盖图画笔
        mBottomBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        //从xml定义属性中拿到对应数值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.MyView_text:
                    mText = ta.getString(index);
                    break;
                case R.styleable.MyView_textsize:
                    mTextSize = ta.getDimension(index, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyView_bottomBitmap:
                    BitmapDrawable bottomDrawable = (BitmapDrawable) ta.getDrawable(index);
                    mBottomBitmap = bottomDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                    break;
                case R.styleable.MyView_topBitmap:
                    BitmapDrawable topDrawable = (BitmapDrawable) ta.getDrawable(index);
                    mTopBitmap = topDrawable.getBitmap();
                    break;
            }
        }
        //拿到底图的宽高
        mBitmapWidth = mBottomBitmap.getWidth();
        mBitmapHeight = mBottomBitmap.getHeight();
        //初始化字体画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextBounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        mTextPaint.setTextSize(mTextSize);
        //初始化底图和覆盖图画笔
        mBottomBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //拿到测量宽高
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //270--150
        //初始化bitmap的绘制区域
        int a = ((int)(height*1.4) / 2 - mBitmapHeight / 2 - mTextBounds.height() / 2) - ((int)(height*0.45 / 2) + mBitmapHeight / 2 - mTextBounds.height() / 2);
        mBitmapRect = new Rect( (int)(width*1.5) / 2 - mBitmapWidth / 2 ,(int)(height*1.4) / 2 - mBitmapHeight / 2 - mTextBounds.height() / 2  ,
                (int)(width*0.5 / 2) + mBitmapHeight / 2  ,  (int)(height*0.45 / 2) + mBitmapHeight / 2 - mTextBounds.height() / 2);
        xText = width / 2 - mTextBounds.width() / 2;
        yText = mBitmapRect.bottom + mTextBounds.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmap(canvas);
        drawText(canvas);
        invalidate();
    }

    private void drawText(Canvas canvas) {
        mTextPaint.setColor(0xcccccc);
        mTextPaint.setAlpha(255 - mAlpha);
        canvas.drawText(mText, xText, yText, mTextPaint);
        mTextPaint.setColor(0x44aaff);
        mTextPaint.setAlpha(mAlpha);
        canvas.drawText(mText, xText, yText, mTextPaint);
    }

    private void drawBitmap(Canvas canvas) {
        //初始化画布
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        //画底图
        mBottomBitmapPaint.setAlpha(255 - mAlpha);
        mCanvas.drawBitmap(mBottomBitmap, null, mBitmapRect, mBottomBitmapPaint);
        //画覆盖图
        mTopBitmapPaint.setAlpha(mAlpha);
        mCanvas.drawBitmap(mTopBitmap, null, mBitmapRect, mTopBitmapPaint);
        //LogUtils.E("alpha==" + mAlpha);
        //将底图和覆盖图的综合效果绘制出来
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    public void setAlpha(float alpha) {
        int ceil = (int) Math.ceil(255 * alpha);
        mAlpha = ceil;
        invalidate();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
