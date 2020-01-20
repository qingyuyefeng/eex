package com.eex.mvp.assrtsjava.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.home.bean.MainData;

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
 * @Package: com.overthrow.mvp.assrtsjava.adapter
 * @ClassName: CommMoneyDetailsAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 17:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 17:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CommMoneyDetailsAdapter extends RecyclerAdapter<MainData> {


    public CommMoneyDetailsAdapter(ArrayList<MainData> dataList) {
        super(R.layout.re_item_comm_details, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainData item) {


        String s = item.getDelKey().substring(0, item.getDelKey().indexOf("_"));

        helper.setText(R.id.item_name, s);

        helper.setText(R.id.item_name2, "(" + s + ")");


        try {
            BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
            if (newData.compareTo(BigDecimal.ZERO) == 1) {
                helper.setText(R.id.item_percentage, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                helper.setTextColor(R.id.item_percentage, CommonUtil.getColor(R.color.gred));
            } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                helper.setTextColor(R.id.item_percentage, CommonUtil.getColor(R.color.appbar_background3));
                helper.setText(R.id.item_percentage, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
            } else {
                helper.setTextColor(R.id.item_percentage, CommonUtil.getColor(R.color.appbar_background3));
                helper.setText(R.id.item_percentage, "0.00%");
            }
        } catch (Exception e) {
            helper.setTextColor(R.id.item_percentage, CommonUtil.getColor(R.color.appbar_background3));
            helper.setText(R.id.item_percentage, "---");
        }


        helper.setText(R.id.item_price, item.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
        helper.setText(R.id.item_scny, "≈" + item.getUsdtCny().setScale(2, BigDecimal.ROUND_DOWN));

    }
}
