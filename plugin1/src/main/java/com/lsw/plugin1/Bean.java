package com.lsw.plugin1;

import com.lsw.pluginlibrary.IBean;
import com.lsw.pluginlibrary.ICallback;

/**
 * Created by sweeneyliu on 2018/10/24.
 */
public class Bean implements IBean{

    private String name = "liushuwei";
    private ICallback callback;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void register(ICallback callBack) {
        this.callback = callback;
        //clickButton();
    }

    public void clickButton() {
        callback.sendResult("Hello: " + this.name);
    }

}
