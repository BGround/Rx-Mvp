package com.rx.mvp.cn.model;

import android.app.Application;
import android.content.Context;

import com.r.http.cn.RHttp;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author ZhongDaFeng
 * @date 2018/8/20
 */

public class RApp extends Application {

    /*http请求基础路径*/
    public static final String BASE_API = "http://apicloud.mob.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        RHttp.Configure.get()
                .baseUrl(BASE_API)                   //基础URL
                .init(this);                        //初始化

        refWatcher = initLeakCanary();
    }


    private RefWatcher refWatcher;

    private RefWatcher initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        RApp leakApplication = (RApp) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    /**
     * 监控内存泄漏
     *
     * @param context
     * @param object
     */
    public static void watchLeakCanary(Context context, Object object) {
        RefWatcher refWatcher = RApp.getRefWatcher(context);
        refWatcher.watch(object);
    }

}
