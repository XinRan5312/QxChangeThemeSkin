package com.xinran.changeskinlib.attr;

import android.view.View;
import android.widget.TextView;

import com.xinran.changeskinlib.load.SkinManager;
import com.xinran.changeskinlib.util.L;


/**
 * Created by qixinh on 16/5/7.
 */
public class TextColorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                L.i("applyAttr", "TextColorAttr");
                tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
            }
        }
    }
}
