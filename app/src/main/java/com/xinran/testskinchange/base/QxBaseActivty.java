package com.xinran.testskinchange.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.xinran.changeskinlib.base.QxSkinBaseActivity;
import com.xinran.testskinchange.MainActivity;
import com.xinran.testskinchange.R;

/**
 * Created by qixinh on 16/5/9.
 */
public abstract class QxBaseActivty extends QxSkinBaseActivity {
    public static int newTheme = R.style.NewTheame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initDataBeforeInitView();
        initView();
        initDataAfterInitView();
    }

    protected abstract void initDataAfterInitView();

    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initDataBeforeInitView();

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));

    }

    protected void startActivityWithNoExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public void setContentView(View view) {
        //可以添加一些所有Activity都有的布局比如titlebar等
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        //可以添加一些所有Activity都有的布局比如titlebar等
        super.setContentView(view, params);
    }
    public void changeTheme(int theme){
        newTheme = theme;
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        overridePendingTransition(0, R.anim.activity_close);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open, 0);
    }
    protected <T extends View> T findViewWithId(int id) {
        return (T) super.findViewById(id);
    }
}
