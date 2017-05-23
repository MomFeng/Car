package com.hncst.administrator.car.util

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.widget.TextView
import android.widget.Toast
import com.example.administrator.car.R

/**
 * Kotlin版本的工具类
 * Created by MomFeng on 2017/5/23 0023.
 */
object KotlinText {

    private var toast: Toast? = null

    /**
     * 一个点击事件的工具类
     * 当前用在MainFragment中
     * @param context
     * @param cls
     */
    fun ToIntent(context: Context, cls: Class<*>) {
        val i = Intent(context, cls)
        context.startActivity(i)
    }

    /**
     * 用于从SharedPreferences快速读取Boolean的一个工具类
     * @param file xml文件名
     * @param _field  字段名
     * @param context  Activity
     * @return  Boolean
     */
    fun GetShareBoolean(file: String, _field: String, context: Context): Boolean? {
        val sp = context.getSharedPreferences(file, Context.MODE_PRIVATE)
        val b = sp.getBoolean(_field, false)
        return b
    }

    /**
     * 用于快速写入Boolean到SharedPreferences的工具类
     * @param file  xml文件名
     * @param _field  字段名
     * @param b  变量
     * @param context  Activity
     */
    fun SetShareBoolean(file: String, _field: String, b: Boolean?, context: Context) {
        val sp = context.getSharedPreferences(file, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(_field, b!!)
        editor.commit()
    }

    /**
     * 展示Toast的方法
     * @param context context
     * @param content 内容
     */
    fun showToast(context: Context, content: String) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(content)
        }
        toast!!.show()
    }

    /**
     * 更改snackbar弹出消息的字体颜色
     * @param snackbar
     * @param color
     */
    fun setSnackbarMessageTextColor(snackbar: Snackbar, color: Int) {
        val view = snackbar.view
        (view.findViewById(R.id.snackbar_text) as TextView).setTextColor(color)
    }
}