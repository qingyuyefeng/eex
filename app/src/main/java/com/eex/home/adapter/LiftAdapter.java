package com.eex.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eex.R;
import com.eex.home.bean.SecondRecordlist;

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
 * @ClassName: LiftAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/12 17:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/12 17:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LiftAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private List<SecondRecordlist> memactivity_uncompleted_list;

    public LiftAdapter(Activity mContext, List<SecondRecordlist> memactivity_uncompleted_list) {
        this.mContext = mContext;
        this.memactivity_uncompleted_list = memactivity_uncompleted_list;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (memactivity_uncompleted_list.size() == 0)
            return 0;
        return memactivity_uncompleted_list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHouder viewHouder;
        viewHouder = new ViewHouder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_miaosha, null);
            viewHouder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHouder);
        } else {
            viewHouder = (ViewHouder) convertView.getTag();
        }
        viewHouder.name.setText(memactivity_uncompleted_list.get(position).getCoinCode());

        return convertView;
    }

    class ViewHouder {
        private TextView name;
    }
}
