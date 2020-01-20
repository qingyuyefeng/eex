package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.Accumulative24H;

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
 * @ClassName: Accumulative24HListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 21:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 21:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 24H待分红累计
 */
public class Accumulative24HListAdapter extends RecyclerAdapter<Accumulative24H> {


    public Accumulative24HListAdapter(ArrayList<Accumulative24H> list) {
        super(R.layout.item_accumulative,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Accumulative24H item) {

        helper.setText(R.id.tx_coin,item.getCoinCode());
        helper.setText(R.id.tx_meMoney,item.getTotalWelfare().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.tx_DbiMoney,item.getCircuOneEbtWelfare().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.tx_suocang_money,item.getLockOneEbtWelfare().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
    }
}
