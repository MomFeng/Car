package com.example.administrator.car.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.car.Interface.BindLayout;
import com.example.administrator.car.Interface.BindView;
import com.example.administrator.car.Interface.BindonClick;
import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;
import com.example.administrator.car.util.GlideCacheUtil;
import com.example.administrator.car.view.SwitchView;

import java.util.Locale;

/**
 * 设置的Activity
 * Created by MomFeng on 2017/4/16 0016.
 */

@BindLayout(R.layout.activity_setting)
public class SettingActivity extends MyActivity{

    //返回
    @BindView(R.id.btn_setting_back)
    Button btn_setting_back;
    //语言
    @BindView(R.id.lea_setting_default)
    LinearLayout lea_setting_default;
    @BindView(R.id.lea_setting_simplechinese)
    LinearLayout lea_setting_simplechinese;
    @BindView(R.id.lea_setting_traditionalchinese)
    LinearLayout lea_setting_traditionalchinese;
    @BindView(R.id.lea_setting_english)
    LinearLayout lea_setting_english;
    //语言选择
    @BindView(R.id.img_setting_default)
    ImageView img_setting_default;
    @BindView(R.id.img_setting_simplechinese)
    ImageView img_setting_simplechinese;
    @BindView(R.id.img_setting_traditionalchinese)
    ImageView img_setting_traditionalchinese;
    @BindView(R.id.img_setting_english)
    ImageView img_setting_english;
    //三个点击LinearLayout
    @BindView(R.id.lea_intent_setting)
    LinearLayout lea_intent_setting;
    @BindView(R.id.lea_bluetooth_setting)
    LinearLayout lea_bluetooth_setting;
    @BindView(R.id.lin_setting_pulldoor)
    LinearLayout lin_setting_pulldoor;
    //仿IOS点击按钮
    @BindView(R.id.sv_intent_setting)
    SwitchView sv_intent_setting;
    @BindView(R.id.sv_bluetooth_setting)
    SwitchView sv_bluetooth_setting;
    @BindView(R.id.sv_pulldoor_setting)
    SwitchView sv_pulldoor_setting;
    //缓存
    @BindView(R.id.lea_setting_cleancache)
    LinearLayout lea_setting_cleancache;
    @BindView(R.id.tv_setting_cleancache)
    TextView tv_setting_cleancache;

    private int[] imgs = {R.id.img_setting_default,R.id.img_setting_simplechinese,R.id.img_setting_traditionalchinese,R.id.img_setting_english};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化控件
        initView();
        //初始化点击事件
        initEvent();
    }

    private void initEvent() {
        SharedPreferences sp = getSharedPreferences("config" , MODE_PRIVATE);
        int dex = sp.getInt("Language" , 0);
        if(dex == 0){
            setBackcorrect(img_setting_default);
        }else if(dex == 1){
            setBackcorrect(img_setting_simplechinese);
        }else if(dex == 2){
            setBackcorrect(img_setting_traditionalchinese);
        }else{
            setBackcorrect(img_setting_english);
        }

        tv_setting_cleancache.setText(GlideCacheUtil.getInstance().getCacheSize(SettingActivity.this));
    }

    private void initView() {

    }

    @BindonClick({R.id.btn_setting_back,R.id.lea_setting_default,R.id.lea_setting_simplechinese,R.id.lea_setting_traditionalchinese,
            R.id.lea_setting_english,R.id.lea_intent_setting,R.id.lea_bluetooth_setting,R.id.lin_setting_pulldoor,R.id.lea_setting_cleancache})
    public void myOnClick(View v) {
        Resources resources = SettingActivity.this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Intent intent;

        switch (v.getId()){
            case R.id.btn_setting_back:
                SettingActivity.this.finish();
                break;
            case R.id.lea_setting_default:
                // 应用用户选择语言
                config.locale = Locale.getDefault();
                resources.updateConfiguration(config, dm);
                setshapepreferences(0);
                setBackcorrect(img_setting_default);
                //Toast.makeText(SettingActivity.this,"下次启动程序后启用新语言",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SettingActivity.this.startActivity(intent);
                break;
            case R.id.lea_setting_simplechinese:
                // 应用用户选择语言
                config.locale = Locale.SIMPLIFIED_CHINESE;
                resources.updateConfiguration(config, dm);
                setshapepreferences(1);
                setBackcorrect(img_setting_simplechinese);
                //Toast.makeText(SettingActivity.this,"下次启动程序后启用新语言",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SettingActivity.this.startActivity(intent);
                break;
            case R.id.lea_setting_traditionalchinese:
                config.locale = Locale.TRADITIONAL_CHINESE;
                resources.updateConfiguration(config, dm);
                setshapepreferences(2);
                setBackcorrect(img_setting_traditionalchinese);
                //Toast.makeText(SettingActivity.this,"下次启动程序后启用新语言",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SettingActivity.this.startActivity(intent);
                break;
            case R.id.lea_setting_english:
                config.locale = Locale.ENGLISH;
                resources.updateConfiguration(config, dm);
                setshapepreferences(3);
                setBackcorrect(img_setting_english);
                //Toast.makeText(SettingActivity.this,"下次启动程序后启用新语言",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SettingActivity.this.startActivity(intent);
                break;
            //网络模块的按钮
            case R.id.lea_intent_setting:
                if(sv_intent_setting.getcheck()){
                    sv_intent_setting.setcheck(false);
                }else{
                    sv_intent_setting.setcheck(true);
                }
                break;
            case R.id.lea_bluetooth_setting:
                if(sv_bluetooth_setting.getcheck()){
                    sv_bluetooth_setting.setcheck(false);
                }else{
                    sv_bluetooth_setting.setcheck(true);
                }
                break;
            //炫酷锁屏点击事件
            case R.id.lin_setting_pulldoor:
                if(sv_pulldoor_setting.getcheck()){
                    sv_pulldoor_setting.setcheck(false);
                }else{
                    sv_pulldoor_setting.setcheck(true);
                }
                break;
            //清除缓存点击事件
            case R.id.lea_setting_cleancache:
                GlideCacheUtil.getInstance().clearImageAllCache(SettingActivity.this);
                break;
        }
    }

    //修改视图显示的方法
    public void setBackcorrect(ImageView img){
        for (int i = 0; i < imgs.length; i++) {
            ImageView img_white = (ImageView) findViewById(imgs[i]);
            img_white.setImageResource(R.drawable.ic_white);
        }
        img.setImageResource(R.mipmap.ic_correct);
    }

    //写入SharedPreferences文件的方法
    public void setshapepreferences(int language){
        SharedPreferences sp = getSharedPreferences("config" , MODE_PRIVATE);
        SharedPreferences.Editor  editor = sp.edit();
        editor.putInt("Language" , language);
        editor.commit();
    }
}
