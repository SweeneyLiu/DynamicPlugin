package com.lsw.pluginlibrary;

/**
 * Created by sweeneyliu on 2018/10/24.
 */
public interface IBean {
    String getName();

    void setName(String name);

    void register(ICallback callBack);
}
