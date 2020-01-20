package com.eex.home.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.adapter.RegionAdapter;
import com.eex.home.bean.PhoneListBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: RegionActicity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 15:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 15:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 国家地区
 */
public class RegionActicity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.RegionActicity_listview)
    ListView RegionActicityListview;

    private RegionAdapter adaper;
    private ArrayList<PhoneListBean> mList = new ArrayList<>();
    private PhoneListBean phoneListVO;

    String Data = "[{name:\"中国大陆地区\",code:\"+86\"}{name:\"其他国家及地区\",code:\"+00\"}]";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_region_acticity;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("国家地区");

        try {
            JSONArray array = new JSONArray(Data.replace("}{","},{"));
            for (int i = 0; i < array.length(); i++) {
                phoneListVO = new PhoneListBean();
                JSONObject temp = (JSONObject) array.get(i);
                String name = temp.getString("name");
                String code = temp.getString("code");
                phoneListVO.setName(name);
                phoneListVO.setCode(code);
                mList.add(phoneListVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initUiAndListener() {
        adaper = new RegionAdapter(RegionActicity.this,mList);
        RegionActicityListview.setAdapter(adaper);
        RegionActicityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name",mList.get(position).getName());
                setResult(2000,intent);
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
