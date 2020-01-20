package com.eex.market.adpater;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.Stoploss;

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
 * @Package: com.overthrow.market.adpater
 * @ClassName: StopHistAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 17:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 17:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StopHistAdapter extends RecyclerAdapter<Stoploss> {


    public StopHistAdapter(ArrayList<Stoploss> data) {
        super(R.layout.item_history, data);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, Stoploss item) {

        helper.setText(R.id.text_name, item.getCoinCode() + "_" + item.getFixPriceCoinCode());
        helper.setText(R.id.text_time, CommonUtil.getTimeSS(item.getCreateTime()));


        helper.setText(R.id.text_Sum, item.getDelAmount().stripTrailingZeros().toPlainString().toString() + " " + item.getFixPriceCoinCode());


        helper.setText(R.id.text_nuber, mContext.getResources().getString(R.string.nuber11) + item.getDelNum().stripTrailingZeros().toPlainString());


        if (item.getTriggerState() == 0) {
            helper.setText(R.id.text_typedata, "未触发");
        }
        if (item.getTriggerState() == 1) {
            helper.setText(R.id.text_typedata, "触发成功");
        }
        if (item.getTriggerState() == 2) {
            helper.setText(R.id.text_typedata, "触法失败");
        }
        if (item.getTriggerState() == 3) {
            helper.setText(R.id.text_typedata, "已撤销");
        }


        if (item.getDealType().toString().equals("1")) {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.buy));
            helper.setText(R.id.text_type, CommonUtil.getColor(R.color.K_bul));
        } else {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.sell));
            helper.setText(R.id.text_type, CommonUtil.getColor(R.color.appbar_background3));
        }

    }
}
