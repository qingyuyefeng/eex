package com.eex.assets.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eex.R;
import com.eex.assets.adpater.BankListAdapter;
import com.eex.assets.bean.BankList;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;
import com.eex.mine.bean.BankListDataVO;

import java.util.ArrayList;
import java.util.List;

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
 * @ClassName: BankListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 15:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 15:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 选择银行卡
 */
public class BankListActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.bankList_Listview)
    ListView bankListListview;

    private List<BankList> itemslist = new ArrayList<>();


    private String BankData = "{data:[{text:\"花旗银行\"},{text:\"浦发银行\"},{text:\"恒丰银行\"},{text:\"兴业银行\"},{text:\"青岛银行\"},{text:\"哈尔滨银行\"},{ text:\"华夏银行\"},{text:\"中国民生银行\"},{text:\"中国工商银行\"},{text:\"中国邮政储蓄银行\"},{ text:\"交通银行\"},{ text:\"中国建设银行\"},{ text:\"广发银行\"},{text:\"中国光大银行\"},{text:\"中国银行\"},{text:\"中国农业银行\"},{ text:\"平安银行\"},{ text:\"招商银行\"}]}";

    private BankListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        net();
        bankListListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("textview", itemslist.get(position).getText());
                setResult(2000, intent);
                BankListActivity.this.finish();
            }
        });
    }

    private void net() {

        BankListDataVO dataVO = CommonUtil.fromJson(BankData, BankListDataVO.class);
        if (dataVO!=null){
            itemslist = dataVO.getData();
            setData();
        }


    }

    private void setData() {
        adapter = new BankListAdapter(BankListActivity.this, itemslist);
        bankListListview.setAdapter(adapter);

    }

    @Override
    protected void initUiAndListener() {

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
