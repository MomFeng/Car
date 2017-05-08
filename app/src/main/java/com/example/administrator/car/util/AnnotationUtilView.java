package com.example.administrator.car.util;

import android.app.Activity;
import android.view.View;

import com.example.administrator.car.Interface.BindLayout;
import com.example.administrator.car.Interface.BindView;
import com.example.administrator.car.Interface.BindonClick;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 运用反射查找activity的方法
 * Created by MomFeng on 2017/5/8 0008.
 */

public class AnnotationUtilView {
    public static void injectActivity(Activity activity) {
        if (null == activity) {
            return;
        }
        Class<Activity> activityClass = (Class<Activity>) activity.getClass();
        if (isEMLayoutBinder(activityClass)) {
            BindLayout layout = activityClass.getAnnotation(BindLayout.class);
            activity.setContentView(layout.value());
        }
        View decorView = activity.getWindow().getDecorView();
        initViews(activityClass.getDeclaredFields(), decorView, activity);
        initOnClick(activityClass.getDeclaredMethods(), decorView, activity);
    }

    private static boolean isEMLayoutBinder(Class<?> c) {
        return c.isAnnotationPresent(BindLayout.class);
    }

    private static boolean isEMViewBinder(Field filed) {
        return filed.isAnnotationPresent(BindView.class);
    }

    private static void initViews(Field[] fields, View view, Object object) {
        View view1;
        for (Field field : fields) {
            if(isEMViewBinder(field)) {
                BindView emView = field.getAnnotation(BindView.class);
                view1 = view.findViewById(emView.value());
                if(null != view1) {
                    try {
                        field.setAccessible(true);
                        field.set(object, view1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static boolean isEMOnClickBinder(Method method) {
        return method.isAnnotationPresent(BindonClick.class);
    }

    private static void initOnClick(Method[] allMethod, View root, Object object) {
        for (Method method : allMethod) {
            if (isEMOnClickBinder(method)) {
                BindonClick onClick = method.getAnnotation(BindonClick.class);
                MyOnClickListener click = new MyOnClickListener(method, object);
                int[] ids = onClick.value();
                for (int id : ids) {
                    root.findViewById(id).setOnClickListener(click);
                }
            }
        }
    }

    static class MyOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mReceiver;

        public MyOnClickListener(Method method, Object receiver) {
            mMethod = method;
            mReceiver = receiver;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mReceiver, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
