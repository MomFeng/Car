package com.example.administrator.car.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.administrator.car.SettingActivity;

import java.util.Locale;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class MyApplication extends Application{

    private boolean isStart;
    private float mHealth;
    private int mHealth_int;

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

    public void setLanguage(){
        SharedPreferences sp = getSharedPreferences("config" , MODE_PRIVATE);
        int dex = sp.getInt("Language" , 0);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if(dex == 0){
            config.locale = Locale.getDefault();
            resources.updateConfiguration(config, dm);
        }else if(dex == 1){
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
        }else if(dex == 2){
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);
        }else{
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);
        }
    }
}
