package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.CTwoCMainAdapter;
import com.eex.home.bean.CTwoCMain;
import com.eex.mine.activity.NewsPersionActivity;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: CTwoCMainActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 15:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 15:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CTwoCMainActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.Textview_Tips)
    TextView TextviewTips;
    /**
     *
     */
    @BindView(R.id.AnswerRecyclerView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.Btn_put)
    Button BtnPut;


    private ArrayList<CTwoCMain> list = new ArrayList<CTwoCMain>();
    private CTwoCMainAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ctwo_cmain;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("C2C");
        String str = "为了保证您接下来更好的知道交易规则,您需要了解<font color='red'>《C2C交易规则》</font>并答题通过后,才能开始C2C交易";

        TextviewTips.setText(Html.fromHtml(str));

        //
        net();

        comtitlebar.setRightText("C2C交易规则");
        comtitlebar.setRightText("C2C交易规则", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "4a43656d9bdc4970910e4ba5d48cf9be");
                intent.setClass(getActivity(), NewsPersionActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void net() {


        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .ruleexam(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 200) {
                        list = new ArrayList<CTwoCMain>();
                        list.clear();
                        if (data.getData() != null && !data.getData().equals("")) {
                            list.addAll(data.getData());
                        }
                        setRecView(list);
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });


    }

    private void setRecView(ArrayList<CTwoCMain> list) {

        adapter = new CTwoCMainAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
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

    @OnClick({R.id.comtitlebar, R.id.Textview_Tips, R.id.Btn_put})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.Textview_Tips:
                break;

            case R.id.Btn_put:

                btnSure();

                break;
            default:
                break;
        }
    }

    /**
     * 提交答题
     */
    @SuppressLint("CheckResult")
    private void btnSure() {

        if (list.size() != adapter.map.size()) {
            Toast.makeText(getActivity(), "答题尚未完成,请仔细阅读", Toast.LENGTH_SHORT).show();
        } else {
            String json = CommonUtil.toJson(adapter.map);
            HashMap<String, String> requestParam = new HashMap<>();
            requestParam.put("param", json);


            ApiFactory.getInstance()
                    .submitruleexam(kv.decodeString("tokenId"), requestParam)
                    .compose(RxSchedulers.io_main())
                    .subscribe(data -> {
                        if (data.getCode() == 159) {
                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                        } else if (data.getCode() == 1065) {
                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                            intent.setClass(getActivity(), BuySellActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {

                    });
        }
    }
}
