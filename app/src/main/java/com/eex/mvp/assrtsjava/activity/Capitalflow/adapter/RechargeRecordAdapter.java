package com.eex.mvp.assrtsjava.activity.Capitalflow.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.activity.RemittanceActivity;
import com.eex.assets.bean.ResultListdata;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity.Capitalflow.adapter
 * @ClassName: RechargeRecordAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 14:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 14:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class RechargeRecordAdapter extends RecyclerAdapter<ResultListdata> {


    public RechargeRecordAdapter(ArrayList<ResultListdata> list) {
        super(R.layout.re_item_rechargerecord, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultListdata item) {

        helper.setText(R.id.record_time, CommonUtil.getTimeSS(item.getCreateTime()));

        if (item.getCurrency() == null) {
            helper.setText(R.id.record_money, item.getDealAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + mContext.getResources().getString(R.string.record_Remarks_rmb));
        } else {
            helper.setText(R.id.record_money, item.getDealAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + item.getCurrency());
        }


        try {
            helper.setText(R.id.record_Remarks, item.getRemark());

        } catch (Exception e) {
            helper.setText(R.id.record_Remarks, "");
        }

        if (item.getDealStatus().equals(1)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_1));

        } else if (item.getDealStatus().equals(2)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_2));
        } else if (item.getDealStatus().equals(3)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_3));
        } else if (item.getDealStatus().equals(4)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_4));
        } else if (item.getDealStatus().equals(5)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_5));
        } else {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_6));

        }

        helper.setOnClickListener(R.id.record_operation, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ID",item.getId());
                intent.setClass(mContext, RemittanceActivity.class);
                mContext.startActivity(intent);
            }
        });


    }
}
