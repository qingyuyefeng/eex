package com.eex.assets.adpater;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.activity.CurrencyDetailsActivity;
import com.eex.assets.bean.Personal;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;

import net.tsz.afinal.FinalBitmap;
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
 * @ClassName: CommonwealFragmentAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommonwealFragmentAdapter extends RecyclerAdapter<Personal> {

    private FinalBitmap fb;


    public CommonwealFragmentAdapter(ArrayList<Personal> list) {
        super(R.layout.item_cmoney_frament, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Personal item) {

        helper.setText(R.id.list_name2, "(" + item.getCoinCode() + ")");

        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_tilte);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl() + "");


        if (item.getCoinName() == null || item.getCoinName().equals("")) {
            helper.setText(R.id.list_Nowmoney, "0");
        } else {
            helper.setText(R.id.list_Nowmoney, "(" + item.getCoinName() + ")");
        }
        if (item.getTotalMoeny().compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.list_Nowmoney, "0");
        } else {
            helper.setText(R.id.list_Nowmoney, item.getTotalMoeny().setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }


        helper.setText(R.id.list_name, item.getCoinCode());
        helper.addOnClickListener(R.id.linear_ll);

        helper.getView(R.id.linear_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {
                    Intent intent = new Intent();
                    intent.putExtra("coin",item.getCoinCode());
                    intent.putExtra("dongjie",item.getFrozenMoney().setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("imgAddress",item.getImgUrl());
                    intent.putExtra("coinName",item.getCoinName());
                    intent.putExtra("CoinMoney",item.getCoinMoney().setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("FrozenMoney",item.getFrozenMoney().setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("TotalMoeny",item.getTotalMoeny().setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.setClass(mContext,CurrencyDetailsActivity.class);
                    mContext.startActivity(intent);


                }
            }
        });



    }
}

