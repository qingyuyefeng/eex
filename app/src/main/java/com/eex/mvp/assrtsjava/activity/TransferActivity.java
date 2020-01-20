package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.adapter.TransferAdapter;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: TransferActivity
 * @Description: 划转
 * @Author: hcj
 * @CreateDate: 2019/12/25 13:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 13:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class TransferActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.Chargebar)
    NewComTitleBar Chargebar;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_back)
    TextView searchBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.AssetsChargeMoney)
    LinearLayout AssetsChargeMoney;

    private ArrayList<CtcaccountList> list = new ArrayList<>();
    private ArrayList<CtcaccountList> mNewList = new ArrayList<>();

    private TransferAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_assets_charge_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        Chargebar.setTitle(getActivity().getResources().getString(R.string.Currency_choice));

        init();
        net();
    }

    private void init() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!searchEdit.getText().toString().trim().equals("")) {
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    } else {

                        mNewList.clear();
                        String sq = searchEdit.getText().toString().trim();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                mNewList.add(list.get(i));
                            }
                            setView();
                        }

                    }

                } else {
                    mNewList = new ArrayList<CtcaccountList>();
                    mNewList.clear();
                    setView();
                }

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setView() {

        adapter = new TransferAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intent.putExtra("coinCode", list.get(position).getCoinCode());
                intent.putExtra("UsableMoney", list.get(position).getUsableMoney().stripTrailingZeros().toPlainString());
                intent.setClass(getActivity(), AssetsChargeMoneActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void net() {
//        HashMap<String, String> hashMap = new HashMap<>();
//        ApiFactory.getInstance()
//                .accountinfo(kv.decodeString("tokenId"), hashMap)
//                .compose(RxSchedulers.io_main())
//                .subscribe(data -> {
//                    if (data.getData() != null) {
//                        Log.e("null", "net: " + data.getData().toString());
//
//                        list.clear();
//                        list.addAll(data.getData().getUserCoinAccount());
//                        adapter.notifyDataSetChanged();
//
//
//                    } else {
//                        t(data.getMsg());
//                    }
//
//                }, throwable -> {
//
//                    Log.e("TAG", "net: " + throwable.toString());
//
//                });


        HashMap<String, String> hashMap = new HashMap<>();
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(ctcaccountData -> {
                    dialog.dismiss();
                    if (ctcaccountData.getData() != null) {
                        list.clear();
                        list.addAll(ctcaccountData.getData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        t(ctcaccountData.getMsg());
                    }
                }, throwable -> {
                    dialog.dismiss();
                });
    }

    @Override
    protected void initUiAndListener() {
        adapter = new TransferAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        intent.putExtra("coinCode", list.get(position).getCoinCode());
        intent.putExtra("UsableMoney", list.get(position).getUsableMoney().stripTrailingZeros().toPlainString());
        intent.setClass(getActivity(), AssetsChargeMoneActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Chargebar, R.id.search_edit, R.id.search_back, R.id.recyclerView, R.id.AssetsChargeMoney})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Chargebar:
                finish();
                break;
            case R.id.search_edit:
                net();
                break;
            case R.id.search_back:
                finish();
                break;
            case R.id.recyclerView:
                break;
            case R.id.AssetsChargeMoney:
                break;
        }
    }
}
