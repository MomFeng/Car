package com.hncst.administrator.car.util

import android.app.Activity
import android.view.View
import com.hncst.administrator.car.Interface.BindLayout
import com.hncst.administrator.car.Interface.BindView
import com.hncst.administrator.car.Interface.BindonClick
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by MomFeng on 2017/6/4 0004.
 */
class AnnotationUtilView_kotlin {
    fun injectActivity(activity: Activity?) {
        if (null == activity) {
            return
        }
        val activityClass = activity.javaClass
        if (isEMLayoutBinder(activityClass)) {
            val layout = activityClass.getAnnotation(BindLayout::class.java)
            activity.setContentView(layout.value)
        }
        val decorView = activity.window.decorView
        initViews(activityClass.declaredFields, decorView, activity)
        initOnClick(activityClass.declaredMethods, decorView, activity)
    }

    private fun isEMLayoutBinder(c: Class<*>): Boolean {
        return c.isAnnotationPresent(BindLayout::class.java)
    }

    private fun isEMViewBinder(filed: Field): Boolean {
        return filed.isAnnotationPresent(BindView::class.java)
    }

    private fun initViews(fields: Array<Field>, view: View, `object`: Any) {
        var view1: View?
        for (field in fields) {
            if (isEMViewBinder(field)) {
                val emView = field.getAnnotation(BindView::class.java)
                view1 = view.findViewById(emView.value)
                if (null != view1) {
                    try {
                        field.isAccessible = true
                        field.set(`object`, view1)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun isEMOnClickBinder(method: Method): Boolean {
        return method.isAnnotationPresent(BindonClick::class.java)
    }

    private fun initOnClick(allMethod: Array<Method>, root: View, `object`: Any) {
        for (method in allMethod) {
            if (isEMOnClickBinder(method)) {
                val onClick = method.getAnnotation(BindonClick::class.java)
                val click = MyOnClickListener(method, `object`)
                val ids = onClick.value
                for (id in ids) {
                    root.findViewById(id).setOnClickListener(click)
                }
            }
        }
    }

    internal class MyOnClickListener(private val mMethod: Method, private val mReceiver: Any) : View.OnClickListener {

        override fun onClick(v: View) {
            try {
                mMethod.isAccessible = true
                mMethod.invoke(mReceiver, v)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
    }
}