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
import java.util.HashMap;

import dalvik.system.DexClassLoader;

public class BaseActivity extends AppCompatActivity {

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;

    protected HashMap<String, PluginInfo> plugins = new HashMap<String, PluginInfo>();

    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录

    protected DexClassLoader classLoader = null;

    //apk名称
    protected String pluginName = "plugin1-debug.apk";
    protected String plugin1 = "plugin1.apk";
    protected String plugin2 = "plugin2.apk";

    TextView tv;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        Utils.extractAssets(newBase, pluginName);

        Utils.extractAssets(newBase, plugin1);
        Utils.extractAssets(newBase, plugin2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        genegatePluginInfo(pluginName);
        genegatePluginInfo(plugin1);
        genegatePluginInfo(plugin2);
    }

    /**
     * @param pluginName
     */
    protected void genegatePluginInfo(String pluginName) {
        File extractFile = this.getFileStreamPath(pluginName);
        /*
         * 1.创建并返回一个指定名称的目录，在这个目录下来存些东西 输出结果为：
         *   getDir():/data/data/com.lsw.dynamic/app_dex
         *   参数int mode是指文件夹的访问权限而并不包括其子文件夹和文件的访问权限
         *   创建文件的权限:MODE_PRIVATE：说明该文件只能被当前的应用程序所读写
         *   MODE_APPEND：以追加方式打开该文件，应用程序可以向该文件中追加内容。
         *   MODE_WORLD_READABLE：该文件的内容可以被其他的应用程序所读取
         *   MODE_WORLD_WRITEABLE：该文件的内容可以被其他的应用程序所读、写
         *
         */
        File fileRelease = getDir("dex", Context.MODE_PRIVATE);
        String dexpath = extractFile.getPath();
        //dexPath:被解压的apk路径，不能为空。
        //optimizedDirectory：解压后的.dex文件的存储路径，不能为空。这个路径强烈建议使用应用程序的私有路径，不要放到sdcard上，否则代码容易被注入攻击。
        //libraryPath：so库的存放路径，可以为空，若有so库，必须填写。
        //parent：父亲加载器，一般为context.getClassLoader(),使用当前上下文的类加载器。
        DexClassLoader classLoader = new DexClassLoader(dexpath, fileRelease.getAbsolutePath(), null, getClassLoader());

        plugins.put(pluginName, new PluginInfo(dexpath, classLoader));
    }

    protected void loadResources(String dexPath) {
        try {
            //创建AssetManager对象
            AssetManager assetManager = AssetManager.class.newInstance();
            //在assetManager对象中获得addAssetPath方法
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            //执行addAssetPath方法
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
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
