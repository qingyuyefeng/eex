package com.eex.market.adpater;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.Delegation;

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
 * @ClassName: HistoryFramentAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 16:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 16:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HistoryFramentAdapter extends RecyclerAdapter<Delegation> {



    public HistoryFramentAdapter(ArrayList<Delegation> list) {
        super(R.layout.item_history, list);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, Delegation item) {

        helper.setText(R.id.text_residuenuber, mContext.getResources().getString(R.string.yesnuber) + ":" + item.getDelNum().subtract(item.getResidueNum()).doubleValue());
        helper.setText(R.id.text_name, item.getCoinCode() + "_" + item.getFixPriceCoinCode());
        helper.setText(R.id.text_time, item.getCreateTime());

        if (item.getDelWay() == 2) {
            helper.setText(R.id.text_Sum, "市价");
        } else {
            helper.setText(R.id.text_Sum, item.getDelAmount().stripTrailingZeros().toPlainString() + " " + item.getFixPriceCoinCode());
        }

        helper.setText(R.id.text_nuber, mContext.getResources().getString(R.string.nuber11) + item.getDelNum().stripTrailingZeros().toPlainString());


        if (item.getDelStatus() == 1) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.type1));
        }
        if (item.getDelStatus() == 2) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.type2));
        }
        if (item.getDelStatus() == 3) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.type3));
        }
        if (item.getDelStatus() == 4) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.type4));
        }
        if (item.getDelStatus() == 5) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.type5));
        }


        if (item.getDealType().toString().equals("1")){
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.buy));
            helper.setTextColor(R.id.text_type,CommonUtil.getColor(R.color.K_bul) );
        }else {
            helper.setText(R.id.text_type, mContext.getResources().getString(R.string.sell));
            helper.setTextColor(R.id.text_type,CommonUtil.getColor(R.color.appbar_background3) );
        }

    }
}
