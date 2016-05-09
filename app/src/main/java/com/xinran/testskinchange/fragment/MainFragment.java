package com.xinran.testskinchange.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ren.solid.materialdesigndemo.R;
import ren.solid.materialdesigndemo.adapter.FindPagerAdapter;
import ren.solid.materialdesigndemo.fragment.base.BaseFragment;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:29
 */
public class MainFragment extends BaseFragment {

    private static String TAG = "MainFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView");
        mTabLayout = customFindViewById(R.id.sliding_tabs);
        mViewPager = customFindViewById(R.id.viewpager);


        List<String> titles = new ArrayList<>();
        titles.add("找书籍");
        titles.add("找电影");

        FindPagerAdapter viewPagerAdapter = new FindPagerAdapter(getMContext(), titles, getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText("读书"));
        mTabLayout.addTab(mTabLayout.newTab().setText("电影"));
        mTabLayout.setupWithViewPager(mViewPager);

        dynamicAddSkinView(mTabLayout, "tabIndicatorColor", R.color.colorAccent);
    }
}
