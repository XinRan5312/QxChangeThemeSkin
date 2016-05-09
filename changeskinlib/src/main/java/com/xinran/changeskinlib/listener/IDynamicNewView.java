package com.xinran.changeskinlib.listener;

import android.view.View;

import com.xinran.changeskinlib.attr.DynamicAttr;

import java.util.List;


/**
 * Created by qixinh on 16/5/9.
 */
public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
