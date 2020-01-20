package com.eex.mvp.assrtsjava.activity.Capitalflow.adapter;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity.Capitalflow.adapter
 * @ClassName: CoinRecordAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 16:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 16:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CoinRecordAdapter extends RecyclerAdapter<CBlistData> {


    public CoinRecordAdapter(ArrayList<CBlistData> list) {
        super(R.layout.re_item_coinrecord, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CBlistData item) {

        helper.setText(R.id.record_time, CommonUtil.getTime(item.getCreateTime()));
        helper.setText(R.id.CoinRecord_bname, item.getCoinCode());
        helper.setText(R.id.CoinRecord_number, item.getDealAmount().stripTrailingZeros().toPlainString());
        helper.setText(R.id.CoinRecord_sure, item.getConfirNumState());

        if (item.getDealStatus().equals(1)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_1));
        } else if (item.getDealStatus().equals(2)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_2));
        } else if (item.getDealStatus().equals(3)) {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_3));
        } else {
            helper.setText(R.id.record_state, mContext.getResources().getString(R.string.record_adapter_6));
        }
        try {

            helper.setText(R.id.record_Remarks, item.getRemark());
        } catch (Exception e) {
            helper.setText(R.id.record_Remarks, "");

        }

    }
}
