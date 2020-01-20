package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.PaningMoneyDetails;

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
 * @ClassName: MoneyListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MoneyListAdapter extends RecyclerAdapter<PaningMoneyDetails> {

    private long day;
    private long hour;
    private long minute;
    private long second;

    public MoneyListAdapter(ArrayList<PaningMoneyDetails> paningMoneyDetails) {
        super(R.layout.item_money_list, paningMoneyDetails);
    }

    /**
     * 获取系统时间戳
     *
     * @return
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }


    @Override
    protected void convert(BaseViewHolder helper, PaningMoneyDetails item) {

        helper.setText(R.id.tx_oder, "订单号:" + item.getOrderNo());
        helper.setText(R.id.tx_MoneyNuber, "理财数量:" + item.getLockOverplusMoney().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + item.getCoinCode());

        if (item.getCoinCodeProfit() == null) {
            helper.setText(R.id.tx_meMoney, "已得收益:0" + item.getCoinCode());
        } else {

            if (item.getFee() == null) {
                helper.setText(R.id.tx_meMoney, "已得收益:0" );
            } else {
                helper.setText(R.id.tx_meMoney, "已得收益:" + item.getFee().setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
//            helper.setText(R.id.tx_meMoney, "已得收益:" + item.getFee() +"");
            }
        }
        helper.setText(R.id.tx_CurrentTime, item.getLockStartTime());
        if (item.getLevers() == 0) {
            helper.setText(R.id.item_leverage, "杠杠倍数：" + "0");
        } else {
            helper.setText(R.id.item_leverage, "杠杠倍数：" + "X" + item.getLevers() + "   " + item.getLockOverplusMoney().setScale(6, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(item.getLevers())).stripTrailingZeros().toPlainString() + item.getCoinCode());
        }

        final long NowTime = item.getEndDay() - getCurTimeLong();
        if (NowTime > 0) {
            //天
            day = NowTime / (1000 * 60 * 60 * 24);
            //时
            hour = (NowTime - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
            //分
            minute = (NowTime - (day * 1000 * 60 * 60 * 24) - (hour * 1000 * 60 * 60)) / (1000 * 60);
            //秒
            second = (NowTime - (day * 1000 * 60 * 60 * 24) - (hour * 1000 * 60 * 60) - (minute * 1000 * 60)) / 1000;
            if (item.getState() == 3) {
                helper.setText(R.id.tx_MOneyCycle, "已到期");
            } else {
                helper.setText(R.id.tx_MOneyCycle, day + "天" + hour + "时" + minute + "分" + second + "秒");
            }
        } else {
            helper.setText(R.id.tx_MOneyCycle, "已到期");
        }


        if (item.getFinancialCycle() != 0) {
            helper.setText(R.id.tx_MoneyTime, "理财周期:" + item.getFinancialCycle() + "天");
        } else if (item.getFinancialCycleMonth() != 0) {
            helper.setText(R.id.tx_MoneyTime, "理财周期:" + item.getFinancialCycleMonth() + "个月");
        } else {
            helper.setText(R.id.tx_MoneyTime, "理财周期:" + item.getFinancialCycleMonth() + "年");
        }

        if (item.getState() == 1) {
            helper.setText(R.id.tx_moneyType, "已锁仓");
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.appbar_background3));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
            helper.addOnClickListener(R.id.btn_putType);
        } else if (item.getState() == 2) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.tra_main_login_text_gray));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.btn_grd);
            helper.setText(R.id.tx_moneyType, "解仓待审核");
        } else if (item.getState() == 3) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.gred));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.btn_grd);
            helper.setText(R.id.tx_moneyType, "已解仓");
        } else if (item.getState() == 4) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.tra_main_login_text_gray));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
            helper.setText(R.id.tx_moneyType, "解仓审核不通过");
            helper.addOnClickListener(R.id.btn_putType);
        } else {
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.appbar_background3));
            helper.addOnClickListener(R.id.btn_putType);
            helper.setText(R.id.tx_moneyType, "部分已解仓");
        }


        helper.addOnClickListener(R.id.item_linear);
        helper.addOnClickListener(R.id.btn_putType);


    }

}
