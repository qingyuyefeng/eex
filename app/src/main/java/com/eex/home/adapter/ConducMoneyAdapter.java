package com.eex.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.ConducMoneydetails;

import java.util.List;

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
 * @ClassName: ConducMoneyAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 20:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 20:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConducMoneyAdapter extends RecyclerAdapter<ConducMoneydetails> {

    public ConducMoneyAdapter(int layoutResId, @Nullable List<ConducMoneydetails> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConducMoneydetails item) {
        if (item.getFinancialCycle() == 0) {

        } else {
            helper.setVisible(R.id.tx_day, true);
            helper.setVisible(R.id.tx_g1, true);
            helper.setText(R.id.tx_day, item.getFinancialCycle() + "天");
            helper.addOnClickListener(R.id.tx_day);
        }
        if (item.getFinancialCycleMonth() == 0) {

        } else {
            helper.setVisible(R.id.tx_month, true);
            helper.setVisible(R.id.tx_g2, true);
            helper.setText(R.id.tx_month, item.getFinancialCycleMonth() + "个月");
            helper.addOnClickListener(R.id.tx_month);

        }
        if (item.getFinancialCycleYear() == 0) {

        } else {
            helper.setVisible(R.id.tx_year, true);
            helper.setText(R.id.tx_year, item.getFinancialCycleYear() + "年");
            helper.addOnClickListener(R.id.tx_year);
        }
    }
}
