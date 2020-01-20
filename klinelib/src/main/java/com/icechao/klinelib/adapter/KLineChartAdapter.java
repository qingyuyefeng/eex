package com.icechao.klinelib.adapter;

import com.icechao.klinelib.base.BaseKLineChartAdapter;
import com.icechao.klinelib.entity.KLineEntity;
import com.icechao.klinelib.utils.DataTools;

import com.icechao.klinelib.utils.DateUtil;
import com.icechao.klinelib.utils.Dputil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */
public class KLineChartAdapter<T extends KLineEntity> extends BaseKLineChartAdapter<T> {

  private int dataCount;

  private boolean resetShowPosition;

  public boolean getResetShowPosition() {
    return resetShowPosition;
  }

  public void setResetShowPosition(boolean resetShowPosition) {
    this.resetShowPosition = resetShowPosition;
  }

  public List<T> getDatas() {
    return datas;
  }

  private DataTools dataTools;

  public <Q extends DataTools> void setDataTools(Q dataTools) {
    this.dataTools = dataTools;
  }

  private List<T> datas = new ArrayList<>();
  private float[] points;

  public float[] getPoints() {
    return points;
  }

  public KLineChartAdapter() {
    dataTools = new DataTools();
  }

  @Override
  public int getCount() {
    return datas.size();
  }

  @Deprecated
  @Override
  public T getItem(int position) {
    if (dataCount == 0 || position < 0 || position >= dataCount) {
      return null;
    }
    return datas.get(position);
  }

  @Override
  public Date getDate(int position) {
    if (position >= dataCount) {
      return new Date();
    }

    return new Date(datas.get(position).getkTime());
  }

  /**
   * 重置K线数据
   *
   * @param data K线数据
   * @param resetShowPosition 重置K线显示位置default true,如不需重置K线传入false
   */
  public void resetData(List<T> data, boolean resetShowPosition) {
    notifyDataWillChanged();
    datas = data;
    if (null != data && data.size() > 0) {
      this.dataCount = datas.size();
      points = dataTools.calculate(datas);
    } else {
      points = new float[] {};
      this.dataCount = 0;
    }
    this.resetShowPosition = resetShowPosition;
    notifyDataSetChanged();
  }

  /**
   * 重置K线数据
   *
   * @param data K线数据
   */
  public void resetData(List<T> data) {
    resetData(data, true);
  }

  /**
   * 通知K线显示位置发和变化,需要重置时需先设置resetShowPosition为true后调用此方法
   */
  @Override
  public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
  }

  /**
   * 向尾部添加数据
   */
  @Override
  public void addLast(T entity) {
    if (null != entity) {
      datas.add(entity);
      this.dataCount++;
      points = dataTools.calculate(datas);
      notifyDataSetChanged();
    }
  }

  /**
   * 改变某个点的值
   *
   * @param position 索引值
   */
  public void changeItem(int position, T data) {
    datas.set(position, data);
    points = dataTools.calculate(datas);
    notifyDataSetChanged();
  }
}