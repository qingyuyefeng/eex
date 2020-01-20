package com.eex.mvp.trading;

import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.widget.RadioButton;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.bean.getFunCode;
import com.eex.common.base.RecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

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
 * @ProjectName: EEX
 * @Package: com.eex.mvp.trading
 * @ClassName: GearingAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2020/1/6 20:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/1/6 20:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class GearingAdapter extends RecyclerAdapter<Gearing> {

  public SparseBooleanArray mBooleanArray;

  public GearingAdapter(List<Gearing> list) {
    super(R.layout.re_item_recharge, list);
    mBooleanArray = new SparseBooleanArray(list.size());
  }

  @Override
  protected void convert(BaseViewHolder helper, Gearing item) {

    helper.setText(R.id.item_name,item.getLevel() +"X");
    helper.addOnClickListener(R.id.item_name);
    RadioButton button = helper.getView(R.id.item_name);
    if (mBooleanArray.get(helper.getAdapterPosition())) {
      button.setChecked(true);
    } else {
      button.setChecked(false);
    }
  }
}
