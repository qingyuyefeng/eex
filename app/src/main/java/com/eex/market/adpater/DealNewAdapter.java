package com.eex.market.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.PurchaseDatalIst;

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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.market.adpater
 * @ClassName: DealNewAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/25 16:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/25 16:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DealNewAdapter extends RecyclerAdapter<PurchaseDatalIst> {


    public DealNewAdapter(ArrayList<PurchaseDatalIst> datalIsts) {
        super(R.layout.item_new_deal,datalIsts);
    }


    @Override
    protected void convert(BaseViewHolder helper, PurchaseDatalIst item) {

        try {
            if (item.getDealPrice().compareTo(item.getDealPrice()) == 1) {
                helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_red));
            } else {
                helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_bul));
            }
        } catch (Exception e) {
            helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_red));

        }

        helper.setText(R.id.time, CommonUtil.getTimeSS(item.getDealTime()));
        helper.setText(R.id.money, item.getDealPrice().setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());
        helper.setText(R.id.nuber, item.getDealNum().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());

    }
}
