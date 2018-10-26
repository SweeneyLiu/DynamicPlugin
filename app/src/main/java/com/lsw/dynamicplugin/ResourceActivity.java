package com.lsw.dynamicplugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResourceActivity extends BaseActivity {

    /**
     * 需要替换主题的控件
     * 这里就列举三个：TextView,ImageView,LinearLayout
     */
    private TextView textV;
    private ImageView imgV;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        textV = (TextView) findViewById(R.id.text);
        imgV = (ImageView) findViewById(R.id.imageview);
        layout = (LinearLayout) findViewById(R.id.layout);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PluginInfo pluginInfo = plugins.get(plugin1);

                loadResources(pluginInfo.getDexPath());

                doSomething(pluginInfo.getClassLoader(),"com.lsw.plugin1.UIUtil");
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginInfo pluginInfo = plugins.get(plugin2);

                loadResources(pluginInfo.getDexPath());

                doSomething(pluginInfo.getClassLoader(),"com.lsw.plugin2.UIUtil");
            }
        });
    }

    private void doSomething(ClassLoader cl,String className) {
        try {
            Class clazz = cl.loadClass(className);
            Class[] classes = new Class[]{Context.class};
            Object[] objects = new Object[]{this};

            String str = (String) RefInvoke.invokeStaticMethod(clazz, "getTextString", classes, objects);
            textV.setText(str);

            Drawable drawable = (Drawable) RefInvoke.invokeStaticMethod(clazz, "getImageDrawable", classes, objects);
            imgV.setBackgroundDrawable(drawable);

            layout.removeAllViews();
            View view = (View) RefInvoke.invokeStaticMethod(clazz, "getLayout", classes, objects);
            layout.addView(view);

        } catch (Exception e) {
            Log.e("DEMO", "msg:" + e.getMessage());
        }
    }
}
