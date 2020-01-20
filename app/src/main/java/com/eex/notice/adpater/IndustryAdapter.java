package com.eex.notice.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.notice.bean.IndustryBean;
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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.notice.adpater
 * @ClassName: IndustryAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/5 17:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 17:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class IndustryAdapter extends RecyclerAdapter<IndustryBean> {

  public IndustryAdapter(ArrayList<IndustryBean> list) {
    super(R.layout.item_industry, list);
  }

  @Override
  protected void convert(BaseViewHolder helper, IndustryBean item) {
    if (helper.getAdapterPosition() == 0) {
      helper.setVisible(R.id.textViewTime, true);
      helper.setText(R.id.textViewTime,
          item.getCreateTime().substring(0, item.getCreateTime().indexOf(" ")));
    } else {
      if (item.getCreateTime()
          .substring(0, item.getCreateTime().indexOf(" "))
          .equals(getData().get(helper.getAdapterPosition() - 1)
              .getCreateTime()
              .substring(0, item.getCreateTime().indexOf(" ")))) {
        helper.setGone(R.id.textViewTime, false);
      } else {
        helper.setVisible(R.id.textViewTime, true);
        helper.setText(R.id.textViewTime,
            item.getCreateTime().substring(0, item.getCreateTime().indexOf(" ")));
      }
    }
    helper.setText(R.id.tx_time, item.getCreateTime());
    helper.setText(R.id.txname, item.getTitle());
  }
}
