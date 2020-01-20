package com.eex.home.adapter;

import android.util.SparseBooleanArray;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.home.bean.CoinfigList;

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
 * @ClassName: HorizListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 15:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 15:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HorizListAdapter extends BaseQuickAdapter<CoinfigList,BaseViewHolder> {


    public SparseBooleanArray mBooleanArray;


    public HorizListAdapter(ArrayList<CoinfigList> list) {
        super(R.layout.item_hozri_list, list);
        mBooleanArray = new SparseBooleanArray(list.size());
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinfigList item) {

        helper.setText(R.id.button, item.getTradeCoin());
        helper.addOnClickListener(R.id.button);
        RadioButton button = helper.getView(R.id.button);

        if (mBooleanArray.get(helper.getAdapterPosition())) {
            button.setChecked(true);
        } else {
            button.setChecked(false);
        }

    }




}
