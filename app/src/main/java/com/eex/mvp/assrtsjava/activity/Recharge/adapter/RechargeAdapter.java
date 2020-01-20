package com.eex.mvp.assrtsjava.activity.Recharge.adapter;

import android.util.SparseBooleanArray;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.bean.getFunCode;
import com.eex.common.base.RecyclerAdapter;

import java.util.ArrayList;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity.Recharge.adapter
 * @ClassName: RechargeAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 20:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 20:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class RechargeAdapter extends RecyclerAdapter<getFunCode> {

  public SparseBooleanArray mBooleanArray;

  public RechargeAdapter(ArrayList<getFunCode> list) {
    super(R.layout.re_item_recharge, list);
    mBooleanArray = new SparseBooleanArray(list.size());
  }

  @Override
  protected void convert(BaseViewHolder helper, getFunCode item) {

    helper.setText(R.id.item_name,
        item.getCurrencyStr() + mContext.getResources().getString(R.string.recharge));
    helper.addOnClickListener(R.id.item_name);
    RadioButton button = helper.getView(R.id.item_name);
    if (mBooleanArray.get(helper.getAdapterPosition())) {
      button.setChecked(true);
    } else {
      button.setChecked(false);
    }
  }
}
