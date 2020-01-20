package com.eex.assets.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.bean.FunCode;
import com.eex.common.base.RecyclerAdapter;

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
 * @Package: com.overthrow.assets.adpater
 * @ClassName: EpayRechargeAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EpayRechargeAdapter extends RecyclerAdapter<FunCode> {


    public EpayRechargeAdapter(ArrayList<FunCode> list) {
        super(R.layout.item_money_cz_ist, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FunCode item) {

        helper.setText(R.id.tx_name, item.getCurrencyStr() + "提现");
        helper.setText(R.id.tx_shoubi, "(" + item.getCurrency() + ")");
        helper.addOnClickListener(R.id.linear_ll);
    }
}
