package com.example.administrator.car.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.car.Interface.BindLayout;
import com.example.administrator.car.Interface.BindView;
import com.example.administrator.car.Interface.BindonClick;
import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;
import com.example.administrator.car.util.AnnotationUtilView;

/**
 * 登入
 * Created by MomFeng on 2017/5/16 0016.
 */
@BindLayout(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ed_login_username)
    private EditText ed_login_username;
    @BindView(R.id.ed_login_password)
    private EditText ed_login_password;
    @BindView(R.id.btn_login_registered)
    private Button btn_login_registered;
    @BindView(R.id.btn_login_login)
    private Button btn_login_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AnnotationUtilView.injectActivity(this);
        super.onCreate(savedInstanceState);

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



}
