package com.xinran.changeskinlib.attr;

import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;

import com.xinran.changeskinlib.load.SkinManager;


/**
 * Created by qixinh on 16/5/7.
 */
public class CollapsingToolbarLayoutAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof CollapsingToolbarLayout) {
            Log.i("CollapsingToolbarAttr", "apply");
            CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                Log.i("CollapsingToolbarAttr", "apply color");
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                ctl.setContentScrimColor(color);
                ctl.setBackgroundColor(color);
            } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                Log.i("CollapsingToolbarAttr", "apply drawable");
            }
        }
    }
}
