package com.eex.home.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.CTwoCMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 * @ClassName: CTwoCMainAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 10:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 10:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CTwoCMainAdapter extends RecyclerAdapter<CTwoCMain> {


    public Map<String, String> map = new HashMap();

    public CTwoCMainAdapter(ArrayList<CTwoCMain> list) {
        super(R.layout.item_c2c_main, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CTwoCMain item) {

        helper.setText(R.id.title, helper.getPosition() + 1 + "." + item.getTitle());
        helper.setText(R.id.tx_A, item.getOptionA());
        helper.setText(R.id.tx_B, item.getOptionB());
        //C

        if (item.getOptionC() == null || item.getOptionC().equals("")) {
            helper.setGone(R.id.LL_C, false);
        } else {
            helper.setText(R.id.tx_C, item.getOptionC());
        }
        //D
        if (item.getOptionD() == null || item.getOptionD().equals("")) {
            helper.setGone(R.id.LL_D, false);
        } else {
            helper.setText(R.id.tx_D, item.getOptionD());
        }

        CheckBox checkBox1 = helper.getView(R.id.ck_A);
        checkBox1.setClickable(false);
        CheckBox checkBox2 = helper.getView(R.id.ck_B);
        checkBox2.setClickable(false);
        CheckBox checkBox3 = helper.getView(R.id.ck_C);
        checkBox3.setClickable(false);
        CheckBox checkBox4 = helper.getView(R.id.ck_D);
        checkBox4.setClickable(false);

        helper.setOnClickListener(R.id.tx_A, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setChecked(R.id.ck_A, true);
                helper.setChecked(R.id.ck_B, false);
                helper.setChecked(R.id.ck_C, false);
                helper.setChecked(R.id.ck_D, false);
                map.put(item.getId(), "A");

            }
        });
        helper.setOnClickListener(R.id.tx_B, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setChecked(R.id.ck_A, false);
                helper.setChecked(R.id.ck_B, true);
                helper.setChecked(R.id.ck_C, false);
                helper.setChecked(R.id.ck_D, false);
                map.put(item.getId(), "B");

            }
        });
        helper.setOnClickListener(R.id.tx_C, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setChecked(R.id.ck_A, false);
                helper.setChecked(R.id.ck_B, false);
                helper.setChecked(R.id.ck_C, true);
                helper.setChecked(R.id.ck_D, false);
                map.put(item.getId(), "C");

            }
        });
        helper.setOnClickListener(R.id.tx_D, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setChecked(R.id.ck_A, false);
                helper.setChecked(R.id.ck_B, false);
                helper.setChecked(R.id.ck_C, false);
                helper.setChecked(R.id.ck_D, true);
                map.put(item.getId(), "D");

            }
        });


    }
}
