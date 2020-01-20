package com.eex.mvp.assrtsjava.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.base.RecyclerAdapter;

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
 * @ClassName: TransferAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 13:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 13:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class TransferAdapter extends RecyclerAdapter<CtcaccountList> {


    public TransferAdapter(ArrayList<CtcaccountList> list) {
        super(R.layout.re_item_tradingaccount, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CtcaccountList item) {
        helper.setText(R.id.item_name, item.getCoinCode());
        helper.setText(R.id.item_name2, "(" + item.getCoinCode() + ")");

        ImageView imageView = helper.getView(R.id.item_img_tilte);
        Glide.with(mContext).load(WPConfig.PicBaseUrl + item.getImgUrl() + "").into(imageView);

        helper.getView(R.id.item_available1).setVisibility(View.GONE);
        helper.getView(R.id.item_Nowmoney).setVisibility(View.GONE);
    }
}
