package com.baway.wangjiaqi.yuekao_demo02;

import android.app.Application;

/**
 * Created by admin on 2017/8/25.
 */

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
