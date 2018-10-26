package com.lsw.dynamicplugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class BaseActivity extends AppCompatActivity {

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录

    protected DexClassLoader classLoader = null;

    private String pluginName = "plugin1-debug.apk";//apk名称

    TextView tv;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Utils.extractAssets(newBase, pluginName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File extractFile = this.getFileStreamPath(pluginName);
        dexpath = extractFile.getPath();
        /*
         * 1.创建并返回一个指定名称的目录，在这个目录下来存些东西 输出结果为：
         *   getDir():/data/data/com.lsw.dynamic/app_dex
         *   参数int mode是指文件夹的访问权限而并不包括其子文件夹和文件的访问权限
         */
        fileRelease = getDir("dex", 0);
        //dexPath:被解压的apk路径，不能为空。
        //optimizedDirectory：解压后的.dex文件的存储路径，不能为空。这个路径强烈建议使用应用程序的私有路径，不要放到sdcard上，否则代码容易被注入攻击。
        //libraryPath：os库的存放路径，可以为空，若有os库，必须填写。
        //parent：父亲加载器，一般为context.getClassLoader(),使用当前上下文的类加载器。
        classLoader = new DexClassLoader(dexpath, fileRelease.getAbsolutePath(), null, getClassLoader());
    }

    protected void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexpath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
        superRes.getDisplayMetrics();
        superRes.getConfiguration();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }

}
