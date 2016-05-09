package com.xinran.changeskinlib.listener;

/**
 * Created by qixinh on 16/5/9.
 *
 * 加载皮肤时的回调接口
 */
public interface ILoaderListener {
    void onStart();

    void onSuccess();

    void onFailed();
}
