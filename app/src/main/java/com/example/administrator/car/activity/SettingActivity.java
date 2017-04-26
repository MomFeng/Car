package com.example.administrator.car.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.car.R;
import com.example.administrator.car.view.SwitchView;

import java.util.Locale;

/**
 * Created by Administrator on 2017/4/16 0016.
 */

public class SettingActivity extends Activity implements View.OnClickListener {

    private Button btn_setting_back;
    private LinearLayout lea_setting_default,lea_setting_simplechinese,lea_setting_traditionalchinese,lea_setting_english;
    private ImageView img_setting_default,img_setting_simplechinese,img_setting_traditionalchinese,img_setting_english;
    private int[] imgs = {R.id.img_setting_default,R.id.img_setting_simplechinese,R.id.img_setting_traditionalchinese,R.id.img_setting_english};
    private LinearLayout lea_intent_setting,lea_bluetooth_setting;
    private SwitchView sv_intent_setting,sv_bluetooth_setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
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

        btn_setting_back.setOnClickListener(this);
        lea_setting_default.setOnClickListener(this);
        lea_setting_simplechinese.setOnClickListener(this);
        lea_setting_traditionalchinese.setOnClickListener(this);
        lea_setting_english.setOnClickListener(this);
        lea_intent_setting.setOnClickListener(this);
        sv_intent_setting.setOnClickListener(this);
        lea_bluetooth_setting.setOnClickListener(this);
        sv_bluetooth_setting.setOnClickListener(this);
    }

    private void initView() {
        //返回按键
        btn_setting_back = (Button) findViewById(R.id.btn_setting_back);
        //四个语言选项
        lea_setting_default = (LinearLayout) findViewById(R.id.lea_setting_default);
        lea_setting_simplechinese = (LinearLayout) findViewById(R.id.lea_setting_simplechinese);
        lea_setting_traditionalchinese = (LinearLayout) findViewById(R.id.lea_setting_traditionalchinese);
        lea_setting_english = (LinearLayout) findViewById(R.id.lea_setting_english);
        //对应四个语言选项的勾
        img_setting_default = (ImageView) findViewById(R.id.img_setting_default);
        img_setting_simplechinese = (ImageView) findViewById(R.id.img_setting_simplechinese);
        img_setting_traditionalchinese = (ImageView) findViewById(R.id.img_setting_traditionalchinese);
        img_setting_english = (ImageView) findViewById(R.id.img_setting_english);
        //初始化网络开关的LinearLayout
        lea_intent_setting = (LinearLayout) findViewById(R.id.lea_intent_setting);
        sv_intent_setting = (SwitchView) findViewById(R.id.sv_intent_setting);
        lea_bluetooth_setting = (LinearLayout) findViewById(R.id.lea_bluetooth_setting);
        sv_bluetooth_setting = (SwitchView) findViewById(R.id.sv_bluetooth_setting);

    }

    @Override
    public void onClick(View v) {

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
