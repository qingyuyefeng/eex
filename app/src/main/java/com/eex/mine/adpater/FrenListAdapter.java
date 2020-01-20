package com.eex.mine.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.mine.activity.DetileActivity;
import com.eex.mine.bean.Frient;

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
 * @Package: com.overthrow.mine.adpater
 * @ClassName: FrenListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 11:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 11:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FrenListAdapter extends BaseAdapter {
    private Context context;
    private List<Frient> frentData;
    private LayoutInflater inflater;
    private boolean ckb = false;

    public FrenListAdapter(Context context, List<Frient> frentData) {
        this.context = context;
        this.frentData = frentData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (frentData.size() == 0)
            return 0;
        return frentData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHoder viewHoder;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.item_frent_list, null);
            viewHoder.tx_phone = (TextView) convertView.findViewById(R.id.tx_phone);
            viewHoder.persion_nuber1 = (TextView) convertView.findViewById(R.id.persion_nuber1);
            viewHoder.itemNuber = (TextView) convertView.findViewById(R.id.item_nuber);
            viewHoder.ck_type = (CheckBox) convertView.findViewById(R.id.ck_type);
            viewHoder.btn_detile = (Button) convertView.findViewById(R.id.btn_detile);
            viewHoder.Ll_view = (LinearLayout) convertView.findViewById(R.id.Ll_view);
            viewHoder.LL_ONE = (LinearLayout) convertView.findViewById(R.id.LL_ONE);

            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.ck_type.setClickable(false);
        viewHoder.tx_phone.setText(CommonUtil.phoneView(frentData.get(position).getUserName()));
        viewHoder.persion_nuber1.setText(frentData.get(position).getCount()+"");
        viewHoder.itemNuber.setText(frentData.get(position).getEffectiveCount()+"");
        viewHoder.LL_ONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckb == false) {
                    viewHoder.Ll_view.setVisibility(View.VISIBLE);
                    viewHoder.ck_type.setChecked(true);
                    ckb = true;
                } else {
                    viewHoder.Ll_view.setVisibility(View.GONE);
                    viewHoder.ck_type.setChecked(false);
                    ckb = false;
                }

            }
        });
        viewHoder.btn_detile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",frentData.get(position).getId());
                intent.putExtra("Type","1");
                intent.setClass(context,DetileActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHoder {
        TextView tx_phone;
        TextView persion_nuber1;
        TextView itemNuber;
        CheckBox ck_type;
        Button btn_detile;
        LinearLayout Ll_view;
        LinearLayout LL_ONE;

    }
}
