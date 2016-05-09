package com.xinran.changeskinlib.listener;

/**
 * Created by qixinh on 16/5/9.
 *
 * 当主题改变的时候调用，一般Activity和Fragment需要去实现这个接口
 */
public interface ISkinUpdate {
    void onThemeUpdate();
}
