package com.eex.mvp.market.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.constant.Keys;
import com.eex.home.bean.MainList;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;
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
 * @ClassName: MarketSearchAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/8 15:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/8 15:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MarketSearchAdapter extends RecyclerAdapter<MainList> {

  public MarketSearchAdapter(ArrayList<MainList> list1) {
    super(R.layout.re_item_market_search, list1);
  }

  @Override
  protected void convert(BaseViewHolder helper, MainList item) {

    helper.setText(R.id.item_biname, item.getDealPair() + "");

  }
}
