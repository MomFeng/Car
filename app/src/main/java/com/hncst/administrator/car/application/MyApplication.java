package com.hncst.administrator.car.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.hncst.administrator.car.activity.MainActivity;
import com.hncst.administrator.car.service.LockscreenService;
import com.hncst.administrator.car.util.ActivityLifeCycleHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Locale;

/**
 * 全局的一个变量，方法
 * Created by MomFeng on 2017/4/17 0017.
 * 2017/5/5
 * 10066行代码
 */
public class MyApplication extends Application {

    private boolean isStart;
    private float mHealth;
    private int mHealth_int;
    private boolean isrunleft = true;
    private boolean isrunright = true;

    //微信APP_ID
    private String APP_ID = "wxa119176e777bfdfc";

    public IWXAPI api;

    private ActivityLifeCycleHelper mHelper;

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        regToWx();
        mHelper=new ActivityLifeCycleHelper();
        //store all the activities
        registerActivityLifecycleCallbacks(mHelper);
        setLanguage();
        setStart(true);
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public float getmHealth() {
        return mHealth;
    }

    public void setmHealth(float mHealth) {
        this.mHealth = mHealth;
    }

    public int getmHealth_int() {
        return mHealth_int;
    }

    public void setmHealth_int(int mHealth_int) {
        this.mHealth_int = mHealth_int;
    }

    public boolean getisrunleft() {
        return isrunleft;
    }

    public void setIsrunleft(boolean isrunleft) {
        this.isrunleft = isrunleft;
    }

    public boolean getIsrunright() {
        return isrunright;
    }

    public void setIsrunright(boolean isrunright) {
        this.isrunright = isrunright;
    }

    public void setLanguage() {
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        int dex = sp.getInt("Language", 0);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (dex == 0) {
            config.locale = Locale.getDefault();
            resources.updateConfiguration(config, dm);
        } else if (dex == 1) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
        } else if (dex == 2) {
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);
        } else {
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);
        }
    }

    /**
     * 开启锁屏服务
     */
    public void StartService() {
        Intent i = new Intent(this, LockscreenService.class);
        startService(i);
    }

    /**
     * 关闭锁屏服务
     */
    public void StopService(){
        Intent i = new Intent(this, LockscreenService.class);
        stopService(i);
    }

    public ActivityLifeCycleHelper getHelper() {
        return mHelper;
    }

    //注册应用id到微信
    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        //将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

}
