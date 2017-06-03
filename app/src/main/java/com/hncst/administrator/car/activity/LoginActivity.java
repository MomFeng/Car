package com.hncst.administrator.car.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.BindonClick;
import com.hncst.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;
import com.hncst.administrator.car.application.MyApplication;
import com.hncst.administrator.car.util.SimpleUtil;

/**
 * 登入
 * Created by MomFeng on 2017/5/16 0016.
 */
@BindLayout(R.layout.activity_login)
public class LoginActivity extends MyActivity {

    @BindView(R.id.ed_login_username)
    private EditText ed_login_username;
    @BindView(R.id.ed_login_password)
    private EditText ed_login_password;
    @BindView(R.id.btn_login_registered)
    private Button btn_login_registered;
    @BindView(R.id.btn_login_login)
    private Button btn_login_login;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 做个判断用户是否是第一次启动程序
         * 是的话就让他看更新说明
         */
        if(!SimpleUtil.GetShareBoolean("config" , "isupdate"  , this)){
            //弹出更新说明
            update();
            SimpleUtil.SetShareBoolean("config" , "isupdate" , true , this);
        }
    }

    @BindonClick({R.id.btn_login_registered , R.id.btn_login_login})
    public void myonclick(View v){
        Intent i = null;
        switch(v.getId()){
            case R.id.btn_login_login:

                SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                String usernmae = sp.getString("username" , "");
                String password = sp.getString("password" , "");

                if(ed_login_username.getText().toString().equals("")){
                    //CharSequence htmlusername = Html.fromHtml("<font color='red'>用户名不可为空</font>");
                    ed_login_username.setError("用户名不可为空");
                }else if(ed_login_password.getText().toString().equals("")){
                    ed_login_password.setError("密码不可为空");
                }else if(usernmae.equals(ed_login_username.getText().toString()) && password.equals(ed_login_password.getText().toString())){
                    i = new Intent(LoginActivity.this , MainActivity.class);
                    startActivity(i);
                    LoginActivity.this.finish();
                }else{
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(LoginActivity.this);
                    normalDialog.setIcon(R.mipmap.ic_launcher);
                    normalDialog.setTitle("");
                    normalDialog.setMessage("用户名或密码错误！");
                    normalDialog.setPositiveButton("注册",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    app = (MyApplication) LoginActivity.this.getApplication();
                                    app.setStart(true);
                                    app.setmHealth(0);
                                    app.setmHealth_int(0);
                                    Intent i = new Intent(LoginActivity.this , RegisteredActivity.class);
                                    startActivity(i);
                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                }
                            });
                    normalDialog.show();
                }

                break;
            case R.id.btn_login_registered:
                i = new Intent(LoginActivity.this , RegisteredActivity.class);
                startActivity(i);
                break;
        }
    }

    private void update(){
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(LoginActivity.this);
        updateDialog.setTitle("更新介绍");
        updateDialog.setMessage("2017/5/23更新 ----> V1.0.1\n\n1.新增了更新介绍功能\n2.修复了加载咨讯的几个bug\n3.增加了部分功能的用户体验效果\n4.修复了Android6.0跳转闪退问题" +
                "\n5.增加了滑动返回\n6.修复了滑动返回文字错乱bug\n7.修复了滑动导致页面显示不完整的bug\n8.采用了Kotlin跟Java混编技术");
        updateDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        updateDialog.show();
    }

}
