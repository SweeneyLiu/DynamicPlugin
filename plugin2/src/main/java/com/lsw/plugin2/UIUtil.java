package com.lsw.plugin2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by sweeneyliu on 2018/10/26.
 */
public class UIUtil {
    public static String getTextString(Context ctx){
        return ctx.getResources().getString(R.string.app_name);
    }

    public static Drawable getImageDrawable(Context ctx){
        return ctx.getResources().getDrawable(R.drawable.robert);
    }

    public static View getLayout(Context ctx){
        return LayoutInflater.from(ctx).inflate(R.layout.activity_main, null);
    }
}
