package com.eex.common.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 空 on 2017/2/6 0006.
 *
 * 最好把item封装成一个类，逻辑处理在类里面完成
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    public Context context;
    public List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder cvh = CommonViewHolder.getCVH(convertView, context, getLayout(position),parent);

        bindData(cvh,position);

        return cvh.convertView;
    }

    public abstract int getLayout(int position);

    public abstract void bindData(CommonViewHolder cvh,int position);

}
