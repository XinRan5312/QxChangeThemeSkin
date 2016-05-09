package com.xinran.changeskinlib;

import android.app.Application;

import com.xinran.changeskinlib.load.SkinManager;

/**
 * Created by qixinh on 16/5/6.
 */
public class QxSkinApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }

}
