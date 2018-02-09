package com.example.wuyunqiang.testapp.preloadreact;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.example.wuyunqiang.testapp.gesture.RNGestureHandlerEnabledRootView;
import com.facebook.react.ReactApplication;

import java.util.Map;

/**
 * 预加载工具类
 * Created by Song on 2017/5/10.
 */
public class ReactNativePreLoader {

    private static final Map<String,RNGestureHandlerEnabledRootView> CACHE = new ArrayMap<>();

    /**
     * 初始化ReactRootView，并添加到缓存
     * @param activity
     * @param componentName
     */
    public static void preLoad(Activity activity, String componentName) {

        if (CACHE.get(componentName) != null) {
            return;
        }
        // 1.创建ReactRootView
        RNGestureHandlerEnabledRootView rootView = new RNGestureHandlerEnabledRootView(activity);
        rootView.startReactApplication(
                ((ReactApplication) activity.getApplication()).getReactNativeHost().getReactInstanceManager(),
                componentName,
                null);

        // 2.添加到缓存
        CACHE.put(componentName, rootView);
    }

    /**
     * 获取ReactRootView
     * @param componentName
     * @return
     */
    public static RNGestureHandlerEnabledRootView getReactRootView(String componentName) {
        return CACHE.get(componentName);
    }

    /**
     * 从当前界面移除 ReactRootView
     * @param component
     */
    public static void deatchView(String component) {
        try {
            RNGestureHandlerEnabledRootView rootView = getReactRootView(component);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } catch (Throwable e) {
            Log.e("ReactNativePreLoader",e.getMessage());
        }
    }
}
