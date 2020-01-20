package com.eex.home.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.JiecDeltaleMoneyData;

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
 * @ClassName: MoneyDetailsAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 22:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 22:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 理财明细
 */
public class MoneyDetailsAdapter extends RecyclerAdapter<JiecDeltaleMoneyData> {


    public MoneyDetailsAdapter(ArrayList<JiecDeltaleMoneyData> list) {
        super(R.layout.item_money_details, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiecDeltaleMoneyData item) {


        Log.e(TAG, "convert: " + item.getId());
        Log.e(TAG, "convert: " + item.getUserId());
        Log.e(TAG, "convert: " + item.getCoinMoney());
        Log.e(TAG, "convert: " + item.getCoinCode());
        Log.e(TAG, "convert: " + item.getType());
        Log.e(TAG, "convert: " + item.getState());
        Log.e(TAG, "convert: " + item.getRemark());
        Log.e(TAG, "convert: " + item.getCreateTime());



        helper.setText(R.id.tx_dayfee, item.getType());

        if (item.getCoinMoney().length() >= 9) {
            helper.setText(R.id.tx_nuber, item.getCoinMoney().substring(0, 9) + item.getCoinCode());
        } else {
            helper.setText(R.id.tx_nuber, item.getCoinMoney() + item.getCoinCode());
        }

        helper.setText(R.id.tx_time, item.getCreateTime());
        helper.setText(R.id.tx_type, item.getState());

        if (item.getRemark() != null) {
            helper.setVisible(R.id.tx_deltw, false);
            helper.setText(R.id.tx_deltw, "详情:" + item.getRemark());
        } else {
            helper.setVisible(R.id.tx_deltw, true);
        }
    }
}
