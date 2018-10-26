package com.lsw.dynamicplugin;

import dalvik.system.DexClassLoader;

/**
 * Created by sweeneyliu on 2018/10/26.
 */
public class PluginInfo {
    private String dexPath;
    private DexClassLoader classLoader;

    public PluginInfo(String dexPath, DexClassLoader classLoader) {
        this.dexPath = dexPath;
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getDexPath() {
        return dexPath;
    }
}
