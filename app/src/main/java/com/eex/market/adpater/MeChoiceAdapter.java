package com.eex.market.adpater;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.market.bean.MeChoiceListvo;

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
 * @ClassName: MeChoiceAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 17:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 17:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MeChoiceAdapter extends RecyclerAdapter<MeChoiceListvo> {


    public MeChoiceAdapter(ArrayList<MeChoiceListvo> list) {

        super(R.layout.item_me_choice, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeChoiceListvo item) {

        helper.setText(R.id.tx_Biname1, item.getDealpear().substring(0, item.getDealpear().indexOf("_")) + "/");
        helper.setText(R.id.texview_name1, item.getDealpear().substring(item.getDealpear().indexOf("_") + 1, item.getDealpear().length()));
        helper.setChecked(R.id.ck_item, item.isCktype());

        helper.addOnClickListener(R.id.linear_ll);
    }

    public void ckType(int type) {
        if (type == 1) {
            for (int i = 0; i < getData().size(); i++) {
                getData().get(i).setCktype(true);
            }
            notifyDataSetChanged();
        } else {
            for (int i = 0; i < getData().size(); i++) {
                getData().get(i).setCktype(false);
            }
            notifyDataSetChanged();
        }

        notifyDataSetChanged();
    }
}
