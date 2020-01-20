package com.eex.assets.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.assets.adpater
 * @ClassName: C2CListBiAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 14:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 14:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CListBiAdapter  extends RecyclerAdapter<CtcaccountList> {



    public C2CListBiAdapter(ArrayList<CtcaccountList> data) {
        super(R.layout.item_coin_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CtcaccountList item) {
        helper.setText(R.id.coinName,item.getCoinCode());
    }
}
