package com.example.administrator.car.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.car.R;

/**
 * APP的一个工具类
 * Created by MomFeng on 2017/4/30 0030.
 */

public class SimpleUtil {

    private static Toast toast;

    /**
     * 一个点击事件的工具类
     * 当前用在MainFragment中
     * @param context
     * @param cls
     */
    public static void ToIntent(Context context, Class<?> cls){
        Intent i = new Intent(context , cls);
        context.startActivity(i);
    }

    /**
     * 用于从SharedPreferences快速读取Boolean的一个工具类
     * @param file xml文件名
     * @param _field  字段名
     * @param context  Activity
     * @return  Boolean
     */
    public static Boolean GetShareBoolean(String file , String _field, Context context){
        SharedPreferences sp = context.getSharedPreferences(file , Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean(_field , false);
        return b;
    }

    /**
     * 用于快速写入Boolean到SharedPreferences的工具类
     * @param file  xml文件名
     * @param _field  字段名
     * @param b  变量
     * @param context  Activity
     */
    public static void SetShareBoolean(String file , String _field,Boolean b ,Context context){
        SharedPreferences sp = context.getSharedPreferences(file , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(_field , b);
        editor.commit();
    }

    /**
     * 展示Toast的方法
     * @param context context
     * @param content 内容
     */
    public static void showToast(Context context,String content){
        if(toast == null){
            toast = Toast.makeText(context , content ,Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 更改snackbar弹出消息的字体颜色
     * @param snackbar
     * @param color
     */
    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

}
