package com.lsw.dynamicplugin;

import android.app.Application;
import android.content.Context;

/**
 * 这个类只是为了方便获取全局Context的.
 *
 * Created by sweeneyliu on 2018/10/24.
 */
public class DynamicApplication extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
