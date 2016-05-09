package com.xinran.testskinchange;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.xinran.testskinchange.base.QxBaseActivty;

public class MainActivity extends QxBaseActivty {
    private static String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    private int mCurrentSelectMenuIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            Log.i(TAG, "NULL mCurrentSelectMenuIndex:" + mCurrentSelectMenuIndex);
        else {
            mCurrentSelectMenuIndex = savedInstanceState.getInt("currentSelectMenuIndex", 0);
            Log.i(TAG, "NOT NULL mCurrentSelectMenuIndex:" + mCurrentSelectMenuIndex);
        }
    }

    @Override
    protected int layoutResoursId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAfterInitView() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        mToolbar = findViewWithId(R.id.toolbar);
        mDrawerLayout = findViewWithId(R.id.drawer_layout);
        mNavigationView = findViewWithId(R.id.navigation_view);

        mToolbar.setTitle("首页");
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setNavigationViewItemClickListener();
        initDefaultFragment();
        dynamicAddSkinEnableView(mToolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(mNavigationView.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(mNavigationView, "navigationViewMenu", R.color.colorPrimary);
    }

    @Override
    protected void initDataBeforeInitView() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle("首页");
                        switchFragment(MainFragment.class);
                        break;
                    case R.id.navigation_item_ganhuo:
                        mToolbar.setTitle(getString(R.string.ganhuo));
                        switchFragment(GanHuoFragment.class);
                        break;
                    case R.id.navigation_item_blog:
                        mToolbar.setTitle("我的博客");
                        switchFragment(BlogFragment.class);
                        break;
                    case R.id.navigation_item_custom_view:
                        mToolbar.setTitle("自定义View");
                        switchFragment(CustomViewFragment.class);
                        break;
                    case R.id.navigation_item_switch_theme:
                        mToolbar.setTitle("主题换肤");
                        switchFragment(ChangeSkinFragment.class);
                        break;
                    case R.id.navigation_item_about:
                        mToolbar.setTitle("关于");
                        switchFragment(AboutFragment.class);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    //切换Fragment
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (webViewFragment.canGoBack()) {
                webViewFragment.goBack();
                return;
            }
        }

        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            Snackbar.make(mDrawerLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }
    //init the default checked fragment
    private void initDefaultFragment() {
        Log.i(TAG, "initDefaultFragment");
        mCurrentFragment = ViewUtils.createFragment(MainFragment.class);

        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        mNavigationView.getMenu().getItem(mCurrentSelectMenuIndex).setChecked(true);
//
//        Log.i(TAG, "mNavigationView.getMenu().getItem(0)" + mNavigationView.getMenu().getItem(0).isChecked());
//        Log.i(TAG, "mNavigationView.getMenu().getItem(1)" + mNavigationView.getMenu().getItem(1).isChecked());
    }
}
