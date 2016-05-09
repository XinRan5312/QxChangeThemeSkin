package com.xinran.changeskinlib.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xinran.changeskinlib.attr.DynamicAttr;
import com.xinran.changeskinlib.listener.IDynamicNewView;
import com.xinran.changeskinlib.listener.ISkinUpdate;
import com.xinran.changeskinlib.load.SkinInflaterFactory;
import com.xinran.changeskinlib.load.SkinManager;
import com.xinran.changeskinlib.statusbar.StatusBarBackground;

import java.util.List;

/**
 * Created by qixinh on 16/5/6.
 */
public abstract class QxSkinBaseActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {
    // 当前Activity是否需要响应皮肤更改需求
    private boolean isResponseOnSkinChanging = true;
    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        changeStatusColor();
        setContentView(layoutResoursId());
    }

    protected abstract int layoutResoursId();

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        Log.i("SkinBaseActivity", "onThemeUpdate");
        if (!isResponseOnSkinChanging) {
            return;
        }
        mSkinInflaterFactory.applySkin();
        changeStatusColor();

//        //设置window的背景色
//        Drawable drawable = new ColorDrawable(SkinManager.getInstance().getColorPrimaryDark());
//        getWindow().setBackgroundDrawable(drawable);

    }

    public void changeStatusColor() {
        //如果当前的Android系统版本大于4.4则更改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("SkinBaseActivity", "changeStatus");
            int color = SkinManager.getInstance().getColorPrimaryDark();
            StatusBarBackground statusBarBackground = new StatusBarBackground(
                    this, color);
            if (color != -1)
                statusBarBackground.setStatusBarbackColor();
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }
}
