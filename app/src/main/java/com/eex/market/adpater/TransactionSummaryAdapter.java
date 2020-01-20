package com.eex.market.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.market.bean.DealSummaryPage;

import java.math.RoundingMode;
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
 * @ClassName: TransactionSummaryAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/14 16:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/14 16:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TransactionSummaryAdapter extends RecyclerAdapter<DealSummaryPage> {


    public TransactionSummaryAdapter(ArrayList<DealSummaryPage> list) {
        super(R.layout.item_transactionsummary, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DealSummaryPage item) {

        helper.setText(R.id.item_coinCode, "交易币:" + item.getCoinCode() + "");
        helper.setText(R.id.item_fixPriceCoinCode, "定价币:" + item.getFixPriceCoinCode() + "");


        if (item.getBuyDealNum() != null) {

            helper.setText(R.id.item_buyDealNum, "买入量:" + item.getBuyDealNum().setScale(2, RoundingMode.HALF_UP));
        } else {
            helper.setText(R.id.item_buyDealNum, "买入量:" + "0.00");
        }
        if (item.getBuyServiceCharge() != null) {
            helper.setText(R.id.item_buyServiceCharge, "买入手续费:" + item.getBuyServiceCharge().setScale(2, RoundingMode.HALF_UP));
        } else {
            helper.setText(R.id.item_buyServiceCharge, "买入手续费:" + "0.00");

        }

        if (item.getSellDealNum() != null) {

            helper.setText(R.id.item_sellDealNum, "卖出量:" + item.getSellDealNum().setScale(2, RoundingMode.HALF_UP));
        } else {
            helper.setText(R.id.item_sellDealNum, "卖出量:" + "0.00");
        }
        if (item.getSellServiceCharge() != null) {

            helper.setText(R.id.item_sellServiceCharge, "卖出手续费:" + item.getSellServiceCharge().setScale(2, RoundingMode.HALF_UP));
        } else {
            helper.setText(R.id.item_sellServiceCharge, "卖出手续费:" + "0.00");

        }

        helper.setText(R.id.item_startTime, "开始时间:" + item.getStartTime() + "");
        helper.setText(R.id.item_endTime, "结束时间:" + item.getEndTime() + "");


    }
}
