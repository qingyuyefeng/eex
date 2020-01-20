package com.eex.mvp.market.fragment;

import android.util.SparseBooleanArray;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.mvp.market.bean.DealPairList;


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
 * @Package: com.overthrow.mvp.market.fragment
 * @ClassName: FuturesAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 10:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 10:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class FuturesAdapter extends RecyclerAdapter<DealPairList> {

    public SparseBooleanArray mBooleanArray;

    public FuturesAdapter(ArrayList<DealPairList> list) {
        super(R.layout.re_item_futures, list);
        mBooleanArray = new SparseBooleanArray(list.size());

    }

    @Override
    protected void convert(BaseViewHolder helper, DealPairList item) {

        helper.setText(R.id.item_text, item.getPricingCoin());
        helper.addOnClickListener(R.id.item_text);
        RadioButton button = helper.getView(R.id.item_text);

        if (mBooleanArray.get(helper.getAdapterPosition())) {
            button.setChecked(true);
        } else {
            button.setChecked(false);
        }



    }
}
