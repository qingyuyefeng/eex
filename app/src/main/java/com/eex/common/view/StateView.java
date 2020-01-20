package com.eex.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.eex.R;



/**
 * Created by Administrator on 2017/11/27.
 */

public class StateView extends FrameLayout {

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.state_view, this);
    }






}
