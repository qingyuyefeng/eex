package com.eex.assets.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;

import com.eex.R;
import com.eex.assets.bean.AddressListData;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.util.CommonUtil;
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
 * @ClassName: CurrencyAddresListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 11:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 11:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CurrencyAddresListAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private List<AddressListData> memactivity_uncompleted_list;
    private Activity mContext;
    private int type;
    public MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);

    public CurrencyAddresListAdapter(List<AddressListData> memactivity_uncompleted_list, Activity mContext) {
        this.memactivity_uncompleted_list = memactivity_uncompleted_list;
        this.mContext = mContext;
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
        ViewHoder viewHoder = new ViewHoder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_currencyaddreslist, null);
            viewHoder.tx_title = (TextView) convertView.findViewById(R.id.tx_title);
            viewHoder.tx_time = (TextView) convertView.findViewById(R.id.tx_time);
            viewHoder.tx_BiName = (TextView) convertView.findViewById(R.id.tx_biName);
            viewHoder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.tx_title.setText(memactivity_uncompleted_list.get(position).getWalletAddress());
        viewHoder.tx_time.setText(CommonUtil.getTime(memactivity_uncompleted_list.get(position).getCreateTime()));
        viewHoder.tx_BiName.setText(memactivity_uncompleted_list.get(position).getCoinCode());
        viewHoder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeteleData(position);
                memactivity_uncompleted_list.remove(position);
                notifyDataSetChanged();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext.getIntent().getStringExtra("type") != null) {
                    if (mContext.getIntent().getStringExtra("type").equals("type")) {
                        Intent intent = new Intent();
                        intent.putExtra("name", memactivity_uncompleted_list.get(position).getWalletAddress());
                        mContext.setResult(2000, intent);
                        mContext.finish();
                    }
                }


            }
        });
        return convertView;
    }

    @SuppressLint("CheckResult")
    private void DeteleData(final Integer position) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", memactivity_uncompleted_list.get(position).getId());
        ApiFactory.getInstance()
                .delcoinaddress(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        Toast.makeText(mContext, "成功删除钱包地址!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {

                });

    }

    class ViewHoder {
        private TextView tx_title;
        private TextView tx_time;
        private TextView tx_BiName;
        private Button btn_delete;

    }
}
