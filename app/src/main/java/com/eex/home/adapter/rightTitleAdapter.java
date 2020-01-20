package com.eex.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eex.R;
import com.eex.home.weight.TopPopupVO;

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
 * @ClassName: rightTitleAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class rightTitleAdapter  extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity mContext;
    private List<TopPopupVO> memactivity_uncompleted_list;

    public rightTitleAdapter(Activity mContext, List<TopPopupVO> memactivity_uncompleted_list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHouder viewHouder;
        viewHouder = new ViewHouder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_right,null);
            viewHouder.name = (TextView) convertView.findViewById(R.id.text_id);
            convertView.setTag(viewHouder);
        }else {
            viewHouder = (ViewHouder) convertView.getTag();
        }
        viewHouder.name.setText(memactivity_uncompleted_list.get(position).getName());
        return convertView;
    }
    class ViewHouder{
        private TextView name;
    }
}
