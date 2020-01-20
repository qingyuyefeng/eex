package com.eex.assets.adpater;

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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.assets.adpater
 * @ClassName: CurrencyDetailsAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/17 16:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/17 16:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CurrencyDetailsAdapter extends RecyclerAdapter<MainData> {


    public CurrencyDetailsAdapter(ArrayList<MainData> data) {
        super(R.layout.item_currency, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainData item) {

        try {
            BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
            if (newData.compareTo(BigDecimal.ZERO) == 1) {
                helper.setText(R.id.Percentage, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                helper.setTextColor(R.id.Percentage, CommonUtil.getColor(R.color.gred));
            } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                helper.setTextColor(R.id.Percentage, CommonUtil.getColor(R.color.appbar_background3));
                helper.setText(R.id.Percentage, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
            } else {
                helper.setTextColor(R.id.Percentage, CommonUtil.getColor(R.color.appbar_background3));
                helper.setText(R.id.Percentage, "0.00%");
            }
        } catch (Exception e) {
            helper.setTextColor(R.id.Percentage, CommonUtil.getColor(R.color.appbar_background3));
            helper.setText(R.id.Percentage, "---");
        }

        try {
            if (item.getDelKey() != null) {
                helper.setText(R.id.coinName, item.getDelKey().replace("_", "/"));
            } else {
                helper.setText(R.id.coinName, "--");
            }
        } catch (Exception e) {

        }

        try {
            if (item.getNewPrice() != null) {
                helper.setText(R.id.danjia, item.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
            } else {
                helper.setText(R.id.danjia, "--");
            }
        } catch (Exception e) {
            helper.setText(R.id.danjia, "--");
        }

        try {
            helper.setText(R.id.RMB, "¥" + item.getUsdtCny().setScale(2, BigDecimal.ROUND_DOWN));
        } catch (Exception e) {
            helper.setText(R.id.RMB, "--");
        }

    }
}
