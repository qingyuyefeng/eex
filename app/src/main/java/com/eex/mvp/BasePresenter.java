package com.eex.mvp;

import android.os.Bundle;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void initPageState(Bundle params);

    void onNewParams(Bundle params);

    void detachView();
}
