package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.SecondKillRecord;

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
 * @ClassName: SecondRecordListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 10:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 10:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SecondRecordListAdapter extends RecyclerAdapter<SecondKillRecord> {


    public SecondRecordListAdapter(ArrayList<SecondKillRecord> secondKillRecords) {
        super(R.layout.item_second_list,secondKillRecords);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecondKillRecord item) {

        helper.setText(R.id.tx_oder, "订单号: " + item.getOrderNo());
        helper.setText(R.id.tx_MoneyNuber, "秒杀数量: " + item.getSecKillNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + item.getCoinCode());
        helper.setText(R.id.tx_MoneyTime, "秒杀总价: " + item.getSecKillSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
        helper.setText(R.id.tx_CurrentTime, item.getCreateTime());
        helper.setText(R.id.tx_MOneyCycle, item.getEndDay());
        if (item.getState() == 1) {
            helper.setText(R.id.tx_moneyType, "已锁定");
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.gred));
            helper.addOnClickListener(R.id.btn_putType);
            if (item.getEndDay().equals("已到期")) {
                helper.setText(R.id.tx_MOneyCycle, "0小时0分0秒");
                helper.setText(R.id.btn_putType, "到期解冻");
                helper.setBackgroundRes(R.id.btn_putType, R.drawable.btn_greed);
                helper.setTextColor(R.id.btn_putType, mContext.getResources().getColor(R.color.background_baise));
            } else {
                helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
                helper.setText(R.id.btn_putType, "强制解冻");
                helper.setTextColor(R.id.btn_putType, mContext.getResources().getColor(R.color.background_baise));

            }
            helper.setVisible(R.id.tx_MOneyCycle, true);
            helper.setVisible(R.id.tx_time, true);
            helper.setVisible(R.id.btn_putType, true);
        } else if (item.getState() == 3) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.gred));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.btn_grd);
            helper.setText(R.id.tx_moneyType, "待审核");
            helper.setTextColor(R.id.btn_putType, mContext.getResources().getColor(R.color.money_name));
            if (item.getEndDay().equals("已到期")) {
                helper.setText(R.id.tx_MOneyCycle, "0小时0分0秒");
            } else {
                helper.setText(R.id.tx_MOneyCycle, item.getEndDay());
            }
            helper.setVisible(R.id.tx_MOneyCycle, true);
            helper.setVisible(R.id.tx_time, true);
            helper.setVisible(R.id.btn_putType, false);
        } else if (item.getState() == 4) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.tra_main_login_text_gray));
            helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
            helper.setText(R.id.tx_moneyType, "已解除锁定");
            helper.setVisible(R.id.tx_MOneyCycle, false);
            helper.setVisible(R.id.tx_time, false);
            helper.setVisible(R.id.btn_putType, false);

        } else if (item.getState() == 5) {
            helper.setTextColor(R.id.tx_moneyType, mContext.getResources().getColor(R.color.appbar_background3));
            helper.setText(R.id.tx_moneyType, "审核不通过");
            helper.addOnClickListener(R.id.btn_putType);
            if (item.getEndDay().equals("已到期")) {
                helper.setText(R.id.tx_MOneyCycle, "0小时0分0秒");
                helper.setBackgroundRes(R.id.btn_putType, R.drawable.btn_greed);
                helper.setText(R.id.btn_putType, "到期解冻");
            } else {
                helper.setText(R.id.tx_MOneyCycle, item.getEndDay());
                helper.setBackgroundRes(R.id.btn_putType, R.drawable.backbtn);
                helper.setText(R.id.btn_putType, "强制解冻");
            }
            helper.setVisible(R.id.tx_MOneyCycle, true);
            helper.setVisible(R.id.tx_time, true);
            helper.setVisible(R.id.btn_putType, true);
        }


        helper.addOnClickListener(R.id.btn_putType);

    }



}
