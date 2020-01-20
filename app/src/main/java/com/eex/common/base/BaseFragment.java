package com.eex.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.common.view.StateView;
import com.tencent.mmkv.MMKV;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author 空
 * @date 2017/7/18 0018
 * <p>
 * 目前最完善的
 */

public abstract class BaseFragment extends LazyLoadFragment {


    /**
     * 管理Destroy取消订阅者者
     */
    private CompositeDisposable disposables;
    public View rootView;
    public Context context;
    public int page = 1;
    private Unbinder bind;
    public Intent intent = new Intent();
    public MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);
    private boolean isInitView = false;
    private boolean isVisible = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInit = true;
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_parent, container, false);
            LinearLayout parent = findViewById(R.id.parent);
            View mainView = inflater.inflate(getLayoutId(), parent, false);
            if (hasStateView()) {
                if (getStateView() != null) {
                    parent.addView(getStateView());
                }
            }
            parent.addView(mainView);
            bind = ButterKnife.bind(this, rootView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

            initUiAndListener();
            refreshData(savedInstanceState);

//        } else {
//            refreshData(savedInstanceState);

        }

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCanLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("现在在这里：",this.getClass().getCanonicalName());
    }

    public void t(String msg) {
        CommonUtil.showSingleToast(msg);
    }

    public boolean hasStateView() {
        return false;
    }

    public View getStateView() {
        return new StateView(context);
    }

    public <T extends View> T findViewById(int resId) {
        return (T) rootView.findViewById(resId);
    }

    protected abstract void refreshData(Bundle savedInstanceState);

    protected abstract void initUiAndListener();

    @LayoutRes
    protected abstract int getLayoutId();

    //可能会统计界面
//    public abstract String getFragmentTitle();

    //用于做沉浸式  默认的颜色，如果单个界面不一样需要修改
//    public int getThemeColor() {
//        return ContextCompat.getColor(getActivity(), R.color.color_ff7a8f);
//    }

    public void addDisposables(Disposable d) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(d);
    }

    public void removeDisposables(Disposable d) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.remove(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();

        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.dispose();
    }

}
