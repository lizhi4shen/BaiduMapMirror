package com.example.baidumapmirror;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookEntry implements IXposedHookLoadPackage {

    private static final String TARGET_PACKAGE = "com.baidu.BaiduMap";
    private static final String TAG = "BaiduMapMirror";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(TARGET_PACKAGE)) {
            return;
        }

        XposedBridge.log("[" + TAG + "] 模块已加载");

        XposedHelpers.findAndHookMethod(
            Activity.class,
            "onCreate",
            Bundle.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final Activity activity = (Activity) param.thisObject;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                View decorView = activity.getWindow().getDecorView();
                                if (decorView != null) {
                                    decorView.setScaleX(-1f);
                                    XposedBridge.log("[" + TAG + "] 已镜像: " + activity.getClass().getName());
                                }
                            } catch (Exception e) {
                                XposedBridge.log("[" + TAG + "] 错误: " + e.getMessage());
                            }
                        }
                    }, 300);
                }
            }
        );
    }
}
