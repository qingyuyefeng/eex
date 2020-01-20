package com.eex.mine.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.mine.bean.Detllett;

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
 * @Package: com.overthrow.mine.adpater
 * @ClassName: DetileAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 10:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 10:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DetileAdapter extends RecyclerAdapter<Detllett> {


    public DetileAdapter(ArrayList<Detllett> list) {
        super(R.layout.item_del, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Detllett item) {


        helper.setText(R.id.tx_coin,item.getCoinCode());
        if (item.getHistory().compareTo(BigDecimal.ZERO)==0){
            helper.setText(R.id.tx_meMoney,"0");
        }else {
            helper.setText(R.id.tx_meMoney,item.getHistory().setScale(6, BigDecimal.ROUND_DOWN).toPlainString());
        }

        if (item.getCurrent().compareTo(BigDecimal.ZERO)==0){
            helper.setText(R.id.tx_DbiMoney,"0");
        }else {
            helper.setText(R.id.tx_DbiMoney,item.getCurrent().setScale(6,BigDecimal.ROUND_DOWN).toPlainString());
        }
        if (item.getLc().compareTo(BigDecimal.ZERO)==0){
            helper.setText(R.id.tx_suocang_money,"0");
        }else {
            helper.setText(R.id.tx_suocang_money,item.getLc().setScale(6,BigDecimal.ROUND_DOWN).toPlainString());
        }
    }
}
