package com.eex.mvp.market.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.constant.Keys;
import com.eex.home.bean.MainList;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;

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
 * @ProjectName: Futures
 * @Package: com.futures.market.activity
 * @ClassName: MarketSearchActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/9 16:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/9 16:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 市场搜索
 */
public class MarketSearchActivity extends BaseActivity
    implements BaseQuickAdapter.OnItemClickListener {

  /**
   *
   */
  @BindView(R.id.search_edit)
  EditText searchEdit;
  /**
   *
   */
  @BindView(R.id.search_back)
  TextView searchBack;
  /**
   *
   */
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  private MarketSearchAdapter adapter;

  private ArrayList<MainList> list = new ArrayList<>();

  private ArrayList<MainList> list1 = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.re_activity_search;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    net();
    init();
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
            //Toast.makeText(MarketSearchActivity.this, "", Toast.LENGTH_SHORT).show();
          } else {
            list1.clear();
            String sq = searchEdit.getText().toString().trim();
            for (int i = 0; i < list.size(); i++) {
              if (list.get(i).getDealPair().contains(sq.toUpperCase())) {
                list1.add(list.get(i));
              }
              setView(list1);
            }
          }
        } else {
          list1.clear();
          setView(list1);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private void setView(ArrayList<MainList> list1) {
    adapter = new MarketSearchAdapter(list1);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);
  }

  @SuppressLint("CheckResult")
  private void net() {
    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getCode() == 200) {
            list.clear();
            list.addAll(data.getData());
          }
        }, throwable -> {

        });
  }

  @Override
  protected void initUiAndListener() {

    adapter = new MarketSearchAdapter(list1);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);
  }

  @OnClick({ R.id.search_edit, R.id.search_back })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.search_edit:
        net();
        init();
        break;
      case R.id.search_back:
        finish();
        break;
    }
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    intent = new Intent(getActivity(),MainActivity.class);
    Bundle  bundle= new Bundle();
    bundle.putString(Keys.PARAM_PAIR, list1.get(position).getDealPair());
    bundle.putInt(Keys.MAIN_SELECT, 2);
    bundle.putInt(Keys.TRANS_SELECT, 0);
    intent.putExtra("bundle",bundle);
    startActivity(intent);

  }
}
