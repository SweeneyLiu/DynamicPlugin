package com.lsw.plugin1;

import android.content.Context;

import com.lsw.pluginlibrary.IDynamic;

/**
 * Created by sweeneyliu on 2018/10/26.
 */
public class Dynamic implements IDynamic {
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.plugin1_hello_world);
    }
}
