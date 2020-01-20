package com.eex.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.eex.EEXApp;
import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.SharedPreferencesUtils;
import com.eex.common.view.StateView;
import com.eex.home.activity.login.PermissionActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mmkv.MMKV;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by 空 on 2017/7/18 0018.
 * <p>
 * 命名就是注释
 */

public abstract class BaseActivity extends PermissionActivity {

    /**
     * 管理Destroy取消订阅者
     */
    // 定义保存的文件的名称
    private static final String languageName = "sharedfile";

    private CompositeDisposable disposables;
    private AppCompatActivity activity;

    public int page = 1;
    private LinearLayout parent;
    private Unbinder bind;
    protected Context mContext;
    public Intent intent = new Intent();
    private String lanuage;
    public  MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        disposables = new CompositeDisposable();
        activity = this;
        SharedPreferencesUtils.config(this);



//        ImmersionBar.with(activity).init();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor( R.color.white)
                .keyboardEnable(true)
                .init();
        String language = SharedPreferencesUtils.getLungData(languageName, "");
        if (language.equals("")) {
            luanage(language);
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EEXApp.Companion.addActivity(this);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_base);
        parent = (LinearLayout) findViewById(R.id.parent);
        if (getStateView() != null) {
            parent.addView(getStateView());
        }

        LayoutInflater inflater = getLayoutInflater();
        View child = inflater.inflate(getLayoutId(), parent, false);
        parent.addView(child);
        bind = ButterKnife.bind(this);

        initUiAndListener();
        refreshData(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("现在在这里：",this.getClass().getCanonicalName());
    }

    public void t(String msg) {
        CommonUtil.showSingleToast(msg);
    }

    public void setBackground(int color) {
        parent.setBackgroundColor(CommonUtil.getColor(color));
    }

    public View getStateView() {
        return new StateView(activity);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void refreshData(Bundle savedInstanceState);

    protected abstract void initUiAndListener();

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void addDisposables(Disposable d) {
        if (d != null) {
            disposables.add(d);
        }
    }

    public void removeDisposables(Disposable d) {
        disposables.remove(d);
    }

    //可能会统计界面
//    public abstract String getActivityTitle();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        bind.unbind();
        EEXApp.Companion.removeActivity(activity);
        //必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
    }

    /**
     * 默认语言
     *
     * @param language
     */
    private void luanage(String language) {
        Locale myLocale = new Locale(language);
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = myLocale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (language.equals("en")) {
                config.locale = Locale.ENGLISH;
            }
        }
        config.locale = Locale.ENGLISH;
        resources.updateConfiguration(config, dm);
        //保存设置语言的类型
        SharedPreferencesUtils.putShareData(languageName, language);
    }


}
