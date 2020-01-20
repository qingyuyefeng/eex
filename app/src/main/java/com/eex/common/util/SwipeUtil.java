package com.eex.common.util;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by 空 on 2017/11/17 0017.
 */

public class SwipeUtil {

    public static void init(SwipeRefreshLayout swipeRefresh) {
        swipeRefresh.setRefreshing(true);
    }

    public static void loadCompleted(SwipeRefreshLayout swipeRefresh) {
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(false);
        }
    }

}
