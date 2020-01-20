package com.eex.home.adapter;

import android.os.CountDownTimer;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.OrderDetail;

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
 * @ClassName: C2CMeFramentAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CMeFramentAdapter extends RecyclerAdapter<OrderDetail> {


    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;


    public C2CMeFramentAdapter(ArrayList<OrderDetail> list) {
        super(R.layout.item_c2c_frament_me_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail item) {

        helper.setText(R.id.orderID, item.getOrderNo());
        if (item.getTransactionType() == 1) {
            helper.setText(R.id.transactionType, "卖出");
            helper.setTextColor(R.id.transactionType, mContext.getResources().getColor(R.color.gred));
        } else {
            helper.setText(R.id.transactionType, "买入");
            helper.setTextColor(R.id.transactionType, mContext.getResources().getColor(R.color.appbar_background3));
        }
        try {
            helper.setText(R.id.name, item.getUserAccount());
        } catch (Exception e) {
            helper.setText(R.id.name, "商家");
        }
        helper.setText(R.id.nuber, "交易总金额" + item.getPrice().stripTrailingZeros().toPlainString() + "CNY");
        helper.setText(R.id.xiane, "单价" + item.getUnitPrice().stripTrailingZeros().toPlainString() + "CNY");
        helper.setText(R.id.rate, "数量" + item.getDealNum().stripTrailingZeros().toPlainString() + item.getCoinCode());

        if (item.getState() == 1) {
            data = new CountDownTimer(item.getPayEndTime() - item.getCurrentTime(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    //天
                    day = millisUntilFinished / (1000 * 60 * 60 * 24);
                    //时
                    hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                    //分
                    minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                    //秒
                    second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                    helper.setText(R.id.time, minute + "分" + second + "秒");

                }

                @Override
                public void onFinish() {
                    helper.setText(R.id.time, "00:00:00");
                }
            };
            data.start();
            helper.setText(R.id.price, "剩余付款时间");
            helper.setText(R.id.btn_buy, "待付款");
            helper.setBackgroundRes(R.id.btn_buy, R.drawable.backbtn);
        }
        if (item.getState() == 2) {
            data = new CountDownTimer(item.getConfirmTime() - item.getCurrentTime() + 12 * 60 * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    //天
                    day = millisUntilFinished / (1000 * 60 * 60 * 24);
                    //时
                    hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                    //分
                    minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                    //秒
                    second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                    helper.setText(R.id.time, hour + "时" + minute + "分" + second + "秒");

                }

                @Override
                public void onFinish() {
                    helper.setText(R.id.time, "00:00:00");

                }
            };
            data.start();
            helper.setText(R.id.price, "剩余收款时间");
            helper.setText(R.id.btn_buy, "待确认收款");
            helper.setBackgroundRes(R.id.btn_buy, R.drawable.backbtn);
        }
        if (item.getState() == 3) {
            helper.setText(R.id.price, "");
            helper.setText(R.id.time, "");
            helper.setText(R.id.btn_buy, "已取消");
            helper.setBackgroundRes(R.id.btn_buy, R.drawable.btn_grd);
        }
        if (item.getState() == 4) {
            helper.setText(R.id.price, "");
            helper.setText(R.id.time, "");
            helper.setText(R.id.btn_buy, "已过期");
            helper.setBackgroundRes(R.id.btn_buy, R.drawable.btn_grd);
        }
        if (item.getState() == 5) {
            helper.setText(R.id.price, "");
            helper.setText(R.id.time, "");
            helper.setText(R.id.btn_buy, "已完成");
            helper.setBackgroundRes(R.id.btn_buy, R.drawable.btn_grd);
        }

    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        if (data != null) {
            data.cancel();
            data = null;
        }
    }
}
