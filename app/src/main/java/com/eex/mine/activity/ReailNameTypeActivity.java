package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.PhotoActivity;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: ReailNameTypeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 9:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 9:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReailNameTypeActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.imgtype)
    ImageView imgtype;
    /**
     *
     */
    @BindView(R.id.TxType)
    TextView TxType;
    /**
     *
     */
    @BindView(R.id.TXname)
    TextView TXname;
    /**
     *
     */
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reail_name_type;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("级别2认证完成");
        comtitlebar.setRightText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        net();

    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        if (data.getData().getLevel().equals(3)) {
                            if (data.getData().getAuthStatus().equals(0)) {
                                comtitlebar.setTitle("级别3认证");
                                TxType.setText("级别3认证提交成功");
                                imgtype.setImageResource(R.drawable.dialogyes);
                                TXname.setText("需人工审核(24h审核完成)");
                                button2.setVisibility(View.GONE);
                            } else if (data.getData().getAuthStatus().equals(1)) {
                                comtitlebar.setTitle("级别3认证完成");
                                TxType.setText("已完成级别3认证");
                                imgtype.setImageResource(R.drawable.dialogyes);
                                TXname.setText("");
                                button2.setVisibility(View.GONE);
                            } else {
                                comtitlebar.setTitle("级别3认证");
                                TxType.setText("级别3认证不通过");
                                imgtype.setImageResource(R.drawable.dialogshibai);
                                TXname.setText("原因:" + data.getData().getRemark());
                                button2.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                    }
                }, throwable -> {


                });

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

    @OnClick({R.id.comtitlebar, R.id.imgtype, R.id.TxType, R.id.TXname, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.imgtype:
                break;
            case R.id.TxType:
                break;
            case R.id.TXname:
                break;
            case R.id.button2:
                intent.setClass(getApplication(), PhotoActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
