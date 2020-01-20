package com.eex.market.adpater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
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
 * @Package: com.overthrow.market.adpater
 * @ClassName: ReclierViewNewAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/20 16:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 16:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReclierViewNewAdapter extends BaseQuickAdapter<MainData, BaseViewHolder> {


    public ReclierViewNewAdapter(ArrayList<MainData> data) {

        super(R.layout.item_btc_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainData item) {


        try {
            if (item.getDelKey() != null) {

                helper.setText(R.id.texview_name, item.getDelKey().substring(0, item.getDelKey().indexOf("_")));
                helper.setText(R.id.texview_name1, "/" + item.getDelKey().substring(item.getDelKey().indexOf("_") + 1, item.getDelKey().length()));
            } else {
                helper.setText(R.id.texview_name, "--");
            }
        } catch (Exception e) {

        }

        try {
            helper.setText(R.id.textview_RMB, "¥" + item.getUsdtCny().setScale(2, BigDecimal.ROUND_DOWN));
        } catch (Exception e) {
            helper.setText(R.id.textview_RMB, "--");
        }

        try {
            //((high-low)/low*100)
            BigDecimal high;
            BigDecimal low;
            BigDecimal s1;
            BigDecimal s2;
            BigDecimal s3;

            high = item.getHigePrice();
            low = item.getFooPrice();
            s1 = high.subtract(low);
            s2 = low.multiply(new BigDecimal(100));

            s3 = s2.divide(s1,2, BigDecimal.ROUND_HALF_UP);
//            Log.e(TAG, "convert5: "+s3 );

            helper.setText(R.id.item_floating, s3.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"%");
        } catch (Exception e) {
            helper.setText(R.id.item_floating, "--");
        }



        try {
            if (item.getNewPrice() != null) {
                helper.setText(R.id.textview_newMoney, item.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
            } else {
                helper.setText(R.id.textview_newMoney, "--");
            }
        } catch (Exception e) {
            helper.setText(R.id.textview_newMoney, "--");
        }

        try {
            if (item.getNewPrice() != null && item.getLastPrice() != null) {
                BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                if (newData.compareTo(BigDecimal.ZERO) == 1) {
                    helper.setText(R.id.texview_td, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                    helper.setBackgroundRes(R.id.texview_td, R.drawable.btn_greed);
                } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                    helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
                    helper.setText(R.id.texview_td, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                } else {
                    helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
                    helper.setText(R.id.texview_td, "0.00%");
                }
            } else {
                helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
                helper.setText(R.id.texview_td, "---");
            }
        } catch (Exception e) {

        }

        helper.addOnClickListener(R.id.ll_Btn);

        helper.setText(R.id.texview_name3, "成交量:" + item.getDealNum().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

    }


}
