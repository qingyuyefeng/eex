package com.eex.assets.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;

import com.eex.R;
import com.eex.assets.bean.BankListData;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
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
 * @Package: com.overthrow.assets.adpater
 * @ClassName: BankAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 17:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 17:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BankAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;

    private List<BankListData> memactivity_uncompleted_list;
    public MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);


    public BankAdapter(Activity mContext, List<BankListData> memactivity_uncompleted_list) {
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
        Viewhoder viewhoder = new Viewhoder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bank, null);
            viewhoder.bankName = (TextView) convertView.findViewById(R.id.bankName);
            viewhoder.banknUber = (TextView) convertView.findViewById(R.id.banknUber);
            viewhoder.tx_detele = (TextView) convertView.findViewById(R.id.tx_detele);
            convertView.setTag(viewhoder);
        } else {
            viewhoder = (Viewhoder) convertView.getTag();
        }
        viewhoder.bankName.setText(memactivity_uncompleted_list.get(position).getBankName());
        viewhoder.banknUber.setText(memactivity_uncompleted_list.get(position).getBankCardNo());
        viewhoder.tx_detele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
                memactivity_uncompleted_list.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    @SuppressLint("CheckResult")
    private void deleteData(final Integer position) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("bankId", memactivity_uncompleted_list.get(position).getId());
        ApiFactory.getInstance()
                .delete( kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {
                        memactivity_uncompleted_list.remove(position);
                        Toast.makeText(mContext, data.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }

    class Viewhoder {
        private TextView bankName;
        private TextView banknUber;
        private TextView tx_detele;
    }
}
