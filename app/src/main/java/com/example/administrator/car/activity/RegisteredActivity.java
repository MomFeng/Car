package com.example.administrator.car.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.car.Interface.BindLayout;
import com.example.administrator.car.Interface.BindView;
import com.example.administrator.car.Interface.BindonClick;
import com.example.administrator.car.R;
import com.example.administrator.car.util.AnnotationUtilView;

/**
 * 注册
 * Created by MomFneg on 2017/5/16 0016.
 */
@BindLayout(R.layout.activity_registered)
public class RegisteredActivity extends AppCompatActivity {

    @BindView(R.id.ed_registered_username)
    private EditText ed_registered_username;
    @BindView(R.id.ed_registered_password)
    private EditText ed_registered_password;
    @BindView(R.id.ed_registered_password2)
    private EditText ed_registered_password2;
    @BindView(R.id.btn_registered_finish)
    private Button btn_registered_finish;
    @BindView(R.id.btn_registered_back)
    private Button btn_registered_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AnnotationUtilView.injectActivity(this);
        super.onCreate(savedInstanceState);


    }

    @BindonClick({R.id.btn_registered_finish, R.id.btn_registered_back})
    public void myonclick(View v) {
        switch (v.getId()) {
            case R.id.btn_registered_finish:

                if (ed_registered_username.getText().toString().equals("")) {
                    ed_registered_username.setError("用户名不可为空");
                } else if (ed_registered_password.getText().toString().equals("")) {
                    ed_registered_password.setError("密码不可为空");
                } else if (!ed_registered_password.getText().toString().equals(ed_registered_password2.getText().toString())) {
                    ed_registered_password2.setError("密码与第一次输入不相符");
                } else {
                    SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", ed_registered_username.getText().toString());
                    editor.putString("password", ed_registered_username.getText().toString());
                    editor.commit();

                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(RegisteredActivity.this);
                    normalDialog.setIcon(R.mipmap.ic_launcher);
                    normalDialog.setTitle("");
                    normalDialog.setMessage("注册成功！");
                    normalDialog.setPositiveButton("返回登入界面",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RegisteredActivity.this.finish();
                                }
                            });
                    /*normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                }
                            });*/
                    normalDialog.show();
                }


                break;
            case R.id.btn_registered_back:
                RegisteredActivity.this.finish();
                break;
        }
    }

}
