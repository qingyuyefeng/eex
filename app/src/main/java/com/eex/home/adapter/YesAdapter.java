package com.eex.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.YesListData;

import java.util.ArrayList;
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
 * @Package: com.overthrow.home.adapter
 * @ClassName: YesAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 理财币种
 */
public class YesAdapter extends RecyclerAdapter<YesListData> {


    public YesAdapter(int layoutResId, @Nullable List<YesListData> data) {
        super(layoutResId, data);
    }

    public YesAdapter(ArrayList<YesListData> list) {
        super(R.layout.item_yes_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, YesListData item) {

        helper.setText(R.id.tx_name, item.getCoinCode());
        helper.addOnClickListener(R.id.tx_name);
    }
}
