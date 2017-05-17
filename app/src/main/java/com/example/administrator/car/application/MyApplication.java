package com.example.administrator.car.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.administrator.car.service.LockscreenService;

import java.util.Locale;

/**
 * 全局的一个变量，方法
 * Created by MomFeng on 2017/4/17 0017.
 * <p>
 * 2017/5/5
 * 3880行代码
 */

public class MyApplication extends Application {

    private boolean isStart;
    private float mHealth;
    private int mHealth_int;
    private boolean isrunleft = true;
    private boolean isrunright = true;

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public void StartService() {
        Intent i = new Intent(this, LockscreenService.class);
        startService(i);
    }

    public void StopService(){
        Intent i = new Intent(this, LockscreenService.class);
        stopService(i);
    }
}
