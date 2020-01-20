package com.eex.assets.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.bean.CBlistData;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;

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
 * @ClassName: RechargeMoneyListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 10:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 10:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RechargeMoneyListAdapter extends RecyclerAdapter<CBlistData> {


    public RechargeMoneyListAdapter(ArrayList<CBlistData> list) {
        super(R.layout.item_congbi, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CBlistData item) {

        helper.setText(R.id.text_time, CommonUtil.getTime(item.getCreateTime()));
        helper.setText(R.id.text_code, item.getCoinCode());
        helper.setText(R.id.text_money, item.getDealAmount().stripTrailingZeros().toPlainString());
        helper.setText(R.id.severMoney, item.getConfirNumState());

        if (item.getDealStatus().equals(1)) {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.nb1));
        } else if (item.getDealStatus().equals(2)) {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.nb2));
        } else if (item.getDealStatus().equals(3)) {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.nb3));
        } else {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.nb4));
        }
        try {

            helper.setText(R.id.text_beizu, item.getRemark());
        } catch (Exception e) {
            helper.setText(R.id.text_beizu, "");

        }
    }
}