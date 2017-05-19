package com.hncst.administrator.car.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 锁屏操作页（暂时无用）
 * Created by MomFeng on 2017/4/26 0026.
 */
public class LockLayer {
	private Context mContext;
	private WindowManager mWindowManager;// 窗口管理器
	private View mLockView;// 锁屏视图
	private WindowManager.LayoutParams wmParams;
	private static LockLayer mLockLayer;
	private boolean isLocked;// 是否锁定

	public static synchronized LockLayer getInstance(Context mContext) {
		if (mLockLayer == null) {
			mLockLayer = new LockLayer(mContext);
		}
		return mLockLayer;
	}

	/**
	 * 构造方法
	 *
	 * @param mContext
	 */
	public LockLayer(Context mContext) {
		this.mContext = mContext;
		init();
	}

	/**
	 * 初始化布局
	 */
	private void init() {
		isLocked = false;
		// 获取WindowManager
		mWindowManager = (WindowManager) mContext.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		wmParams = new WindowManager.LayoutParams();
		wmParams.type = LayoutParams.TYPE_SYSTEM_ERROR;// 关键部分
		wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置Window flag
		// wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
		// WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
		// WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
		wmParams.height = WindowManager.LayoutParams.FILL_PARENT;
		wmParams.flags = 1280;
	}

	/**
	 * 锁屏
	 */
	public synchronized void lock() {
		if (mLockView != null && !isLocked) {
			mWindowManager.addView(mLockView, wmParams);
		}
		isLocked = true;
	}

	/**
	 * 解锁
	 */
	public synchronized void unlock() {
		if (mWindowManager != null && isLocked) {
			mWindowManager.removeView(mLockView);
		}
		isLocked = false;
	}

	/**
	 * 更新
	 */
	public synchronized void update() {
		if (mLockView != null && !isLocked) {
			mWindowManager.updateViewLayout(mLockView, wmParams);
		}
	}

	/**
	 * 设置锁屏视图
	 *
	 * @param v
	 */
	public synchronized void setLockView(View v) {
		mLockView = v;
	}
}