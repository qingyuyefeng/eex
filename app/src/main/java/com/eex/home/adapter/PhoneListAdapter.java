package com.eex.home.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.PhoneListBean;

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
 * @Package: com.overthrow.home.adapter
 * @ClassName: PhoneListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/6 13:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 13:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhoneListAdapter extends RecyclerAdapter<PhoneListBean> {


    public PhoneListAdapter(ArrayList<PhoneListBean> list) {
        super(R.layout.item_phone_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhoneListBean item) {

        helper.setText(R.id.tx_guojia,item.getName());
        helper.setText(R.id.tx_nuber,item.getCode());

        helper.addOnClickListener(R.id.item_phone_linear);
    }
}
