package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eex.R;
import com.eex.assets.adpater.RealNameActivityAdapter;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



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
 * @Package: com.overthrow.assets.activity
 * @ClassName: ShiListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 10:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 10:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShiListActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.ShengListview)
    ListView ShengListview;


    private RealNameActivityAdapter adaper;
    private String key;

    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shi_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("市");

        if ( getIntent().getStringExtra("key")!=null){
            key =  getIntent().getStringExtra("key");
        }
        net();
    }

    /**
     * 国家地区市list集合
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("tokenId",kv.decodeString("tokenId"));
        hashMap.put("key",key);
        ApiFactory.getInstance()
                .appbankcode(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isSuccess() ==true){
                        try {
                            JSONArray arr = new JSONArray("["+data.getObj()+"]");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject temp = (JSONObject) arr.get(i);
                                String city = temp.getString("city");
                                mList.add(city);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setView();
                    }

                });
    }

    private void setView() {
        adaper = new RealNameActivityAdapter(ShiListActivity.this,mList);
        ShengListview.setAdapter(adaper);
    }

    @Override
    protected void initUiAndListener() {
        ShengListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(ShiListActivity.this,AddBankActivity.class);
                intent.putExtra("shiNanme",mList.get(position));
                setResult(4000,intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
