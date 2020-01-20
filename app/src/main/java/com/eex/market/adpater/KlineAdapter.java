package com.eex.market.adpater;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.Buy;

import java.math.BigDecimal;
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
 * @Package: com.overthrow.market.adpater
 * @ClassName: KlineAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 15:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 15:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KlineAdapter extends RecyclerAdapter<Buy> {


    public KlineAdapter(List<Buy> buyList) {
        super(R.layout.item_kline_purchase, buyList);
    }
    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, Buy item) {

        helper.setText(R.id.textview_Price, item.getDelAmount().setScale(5, BigDecimal.ROUND_CEILING).toString());
        helper.setTextColor(R.id.textview_Price, CommonUtil.getColor(R.color.K_bul));
        helper.setText(R.id.textview_nuber, item.getResidueNum().setScale(5, BigDecimal.ROUND_CEILING).toString());

    }
}
