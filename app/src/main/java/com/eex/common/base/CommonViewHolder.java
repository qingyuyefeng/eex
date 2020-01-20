package com.eex.common.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 */
// ViewHolder存在的意义：
// 减少findViewById的次数，因为fvbi是解析xml
// 我们在是通过很多个成员变量去记录找好的view
// 把原来不同的成员变量 --> 用同一种方法传入不同的参数去获得
public class CommonViewHolder {

    public static CommonViewHolder getCVH(View convertView, Context context, int itemLayout) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemLayout, null);
            return new CommonViewHolder(convertView);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public static CommonViewHolder getCVH(View convertView, Context context, int itemLayout, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemLayout, parent, false);
            return new CommonViewHolder(convertView);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public static CommonViewHolder getCVH(View convertView, View newVie) {
        if (convertView == null) {
            convertView = newVie;
            return new CommonViewHolder(convertView);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public final View convertView;

    private CommonViewHolder(View convertView) {
        this.convertView = convertView;
        convertView.setTag(this);
    }

    Map<Integer, View> views = new HashMap<>();

    public <T extends View>T  getView(int id) {
        if (views.get(id) == null) {
            views.put(id, convertView.findViewById(id));
        }
        return (T)views.get(id);
    }

    // 泛型的类型推导,可以通过参数推导
//    public <T extends View> T getView(int id, Class<T> viewClass) {
//        return (T) getView(id);
//    }

    // 泛型的类型推导,可以通过返回值类型推导
//    public <T extends View> T getView(int id) {
//        return (T) getView(id);
//    }

    public CommonViewHolder setText(int resId, String text) {
        TextView view = getView(resId);
        view.setText(text);
        return this;
    }

    public CommonViewHolder setBckground(int resId, @ColorInt int resColor) {
        getView(resId).setBackgroundColor(resColor);
        return this;
    }

    public CommonViewHolder setBckground(int resId, Drawable resColor) {
        getView(resId).setBackground(resColor);
        return this;
    }

    public CommonViewHolder setTextColor(int resId, int colorRes) {
        TextView view = getView(resId);
        view.setTextColor(colorRes);
        return this;
    }


}