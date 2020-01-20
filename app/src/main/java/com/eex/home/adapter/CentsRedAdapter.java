package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.CentsRed;

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
 * @ClassName: CentsRedAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 20:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 20:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 我的分红Adapter
 */
public class CentsRedAdapter extends RecyclerAdapter<CentsRed> {


    public CentsRedAdapter(ArrayList<CentsRed> list) {
        super(R.layout.item_cents, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CentsRed item) {

        helper.setText(R.id.tx_coin,item.getCoinCode());
        if (item.getCoinTotalWelfare().compareTo(BigDecimal.ZERO)==1){
            helper.setText(R.id.tx_meMoney,item.getCoinTotalWelfare().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }else {
            helper.setText(R.id.tx_meMoney,"0");
        }

        helper.setText(R.id.tx_DbiMoney,item.getLockTotalWelfare().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.tx_suocang_money,item.getWelfareTotal().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

        helper.addOnClickListener(R.id.item_cents);

    }
}
