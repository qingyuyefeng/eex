package com.eex.mvp.market.fragment;

import android.util.Log;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.MainData;

import java.math.BigDecimal;
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
 * @ProjectName: Futures
 * @Package: com.futures.market.adapter
 * @ClassName: MarketAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/10 9:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/10 9:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MarketAdapter extends RecyclerAdapter<MainData> {

  public MarketAdapter(ArrayList<MainData> data) {

    super(R.layout.item_btc_adapter, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, MainData item) {

    helper.addOnClickListener(R.id.ll_Btn);

    try {
      if (item.getDelKey() != null) {

        helper.setText(R.id.texview_name,
            item.getDelKey().substring(0, item.getDelKey().indexOf("_")));
        helper.setText(R.id.texview_name1, "/" + item.getDelKey()
            .substring(item.getDelKey().indexOf("_") + 1, item.getDelKey().length()));
      } else {
        helper.setText(R.id.texview_name, "--");
      }
    } catch (Exception e) {
      Log.e(TAG, "convert1: " + e.toString());
    }

    try {
      helper.setText(R.id.textview_RMB, "¥" + item.getUsdtCny().setScale(2, BigDecimal.ROUND_DOWN));
    } catch (Exception e) {
      helper.setText(R.id.textview_RMB, "--");
      Log.e(TAG, "convert2: " + e.toString());
    }

    try {
      if (item.getNewPrice() != null) {
        helper.setText(R.id.textview_newMoney,
            item.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
      } else {
        helper.setText(R.id.textview_newMoney, "--");
      }
    } catch (Exception e) {
      helper.setText(R.id.textview_newMoney, "--");
      Log.e(TAG, "convert3: " + e.toString());
    }

    try {
      //((high-low)/low*100)


      BigDecimal high =item.getHigePrice().subtract(item.getFooPrice()).divide(item.getFooPrice(),10, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));

      helper.setText(R.id.item_floating, high.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString() + "%");
    } catch (Exception e) {
      helper.setText(R.id.item_floating, "--");
      Log.e(TAG, "convert4: " + e.toString());
    }

    try {
      if (item.getNewPrice() != null && item.getLastPrice() != null) {

        BigDecimal  newData = item.getNewPrice()
            .subtract(item.getLastPrice())
            .divide(item.getLastPrice(), 5, BigDecimal.ROUND_HALF_UP)
            .multiply(new BigDecimal(100))
            .setScale(5, BigDecimal.ROUND_HALF_UP);
        if (newData.compareTo(BigDecimal.ZERO) == 1) {
          helper.setText(R.id.texview_td, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
          helper.setBackgroundRes(R.id.texview_td, R.drawable.btn_greed);
        } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
          helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
          helper.setText(R.id.texview_td, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
        } else {
          helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
          helper.setText(R.id.texview_td, "0.00%");
        }
      } else {
        helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
        helper.setText(R.id.texview_td, "---");
      }
    } catch (Exception e) {
      Log.e(TAG, "convert5: " + e.toString());
    }

    try {

      helper.setText(R.id.texview_name3,
          "成交量:" + item.getDealNum().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
    } catch (Exception e) {

      Log.e(TAG, "convert6: " + e.toString());
    }
  }
}
