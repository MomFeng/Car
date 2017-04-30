package com.example.administrator.car.util;

import android.content.Context;
import android.content.Intent;

/**
 * APP的一个工具类
 * Created by MomFeng on 2017/4/30 0030.
 */

public class SimpleUtil {

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

}
