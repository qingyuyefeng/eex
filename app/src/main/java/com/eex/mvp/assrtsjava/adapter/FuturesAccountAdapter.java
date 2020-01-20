package com.eex.mvp.assrtsjava.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.mvp.assrtsjava.activity.FuturesAccountDetailsActivity;
import com.eex.mvp.assrtsjava.bean.Assets;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.adapter
 * @ClassName: FuturesAccountAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 13:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 13:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class FuturesAccountAdapter extends RecyclerAdapter<Assets> {

    public FuturesAccountAdapter(List<Assets> mNewList) {
        super(R.layout.re_item_futuresaccount, mNewList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Assets item) {

        helper.setText(R.id.item_name, item.getCoinCode());

        helper.setText(R.id.item_name2, "(" + item.getCoinCode() + ")");


//        ImageView imageView = helper.getView(R.id.item_img_tilte);
//        Glide.with(mContext).load(WPConfig.PicBaseUrl + item.getImgUrl() + "").into(imageView);

        if (item.getUsableMoney().compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.item_Nowmoney, "0");
        } else {
            helper.setText(R.id.item_Nowmoney, item.getUsableMoney().setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }

        helper.getView(R.id.item_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {
                    Intent intent = new Intent();
                    intent.putExtra("coin", item.getCoinCode());
//                    intent.putExtra("imgurl", item.getImgUrl());
                    intent.putExtra("totalAssets", item.getBtc().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("usableMoney", item.getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("frozenMoney", item.getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.setClass(mContext, FuturesAccountDetailsActivity.class);
                    mContext.startActivity(intent);

                }
            }
        });
    }
}
