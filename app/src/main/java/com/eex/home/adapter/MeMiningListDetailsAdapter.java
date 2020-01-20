package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.getOrePool;

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
 * @Package: com.overthrow.home.adapter
 * @ClassName: MeMiningListDetailsAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 10:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 10:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 挖矿详情
 */
public class MeMiningListDetailsAdapter extends RecyclerAdapter<getOrePool> {


    public MeMiningListDetailsAdapter(ArrayList<getOrePool> list) {
        super(R.layout.item_meminming_details, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, getOrePool item) {


        helper.setText(R.id.tx_coin, item.getDealpair());
        helper.setText(R.id.tx_suocang_money, "+" + item.getQuantity().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "EBT");
        helper.setText(R.id.tradeNum, "交易总额:" + item.getTradeNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.sever_money, "手续费:" + item.getServiceNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

        helper.setText(R.id.tx_time, item.getCreateTime());
    }
}
