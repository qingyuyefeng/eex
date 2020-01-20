package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;

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
 * @ClassName: RemittanceActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 15:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 15:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RemittanceActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_note)
    TextView txNote;
    /**
     *
     */
    @BindView(R.id.LL_tisi)
    LinearLayout LLTisi;
    /**
     *
     */
    @BindView(R.id.tx_bankNuber)
    TextView txBankNuber;
    /**
     *
     */
    @BindView(R.id.tx_banlName)
    TextView txBanlName;
    /**
     *
     */
    @BindView(R.id.tx_bankAddres)
    TextView txBankAddres;
    /**
     *
     */
    @BindView(R.id.tx_Name)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.tx_Money)
    TextView txMoney;
    /**
     *
     */
    @BindView(R.id.tx_OrderId)
    TextView txOrderId;
    /**
     *
     */
    @BindView(R.id.ed_note)
    TextView edNote;
    /**
     *
     */
    @BindView(R.id.LL_REAK)
    LinearLayout LLREAK;
    /**
     *
     */
    @BindView(R.id.tx_type)
    TextView txType;


    private String remark;
    private String surname;
    private String givenName;
    private String ID = "";
    private String TransactionNum;
    private String BankName;
    private String AccountNumber;
    private String BankAddress;
    private String AccountName;
    private String TransactionMoney;
    private String Status;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_remittance;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.huikuandna));

        if (getIntent().getStringExtra("ID") != null) {
            ID = getIntent().getStringExtra("ID");
        }
        if (getIntent().getStringExtra("TransactionNum") != null) {
            TransactionNum = getIntent().getStringExtra("TransactionNum");
        }
        if (getIntent().getStringExtra("BankName") != null) {
            BankName = getIntent().getStringExtra("BankName");
        }
        if (getIntent().getStringExtra("AccountNumber") != null) {
            AccountNumber = getIntent().getStringExtra("AccountNumber");
        }
        if (getIntent().getStringExtra("BankAddress") != null) {
            BankAddress = getIntent().getStringExtra("BankAddress");
        }
        if (getIntent().getStringExtra("AccountName") != null) {
            AccountName = getIntent().getStringExtra("AccountName");
        }
        if (getIntent().getStringExtra("TransactionMoney") != null) {
            TransactionMoney = getIntent().getStringExtra("TransactionMoney");
        }
        if (getIntent().getStringExtra("Status") != null) {
            Status = getIntent().getStringExtra("Status");
        }
        if (getIntent().getStringExtra("remark") != null) {
            remark = getIntent().getStringExtra("remark");
        }
        if (getIntent().getStringExtra("surname") != null) {
            surname = getIntent().getStringExtra("surname");
        }
        if (getIntent().getStringExtra("givenName") != null) {
            givenName = getIntent().getStringExtra("givenName");
        }

        if (!ID.equals("")) {
            getData();
        }

    }

    /**
     *
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", ID);
        ApiFactory.getInstance()
                .deposit(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() !=null){

                        if (data.getData().getBankName().equals("epay")){
                            LLTisi.setVisibility(View.GONE);
                            LLREAK.setVisibility(View.GONE);
                        }else {
                            txNote.setText(getResources().getString(R.string.tit) + data.getData().getCode() + getResources().getString(R.string.tit1));
                        }


                        txBankNuber.setText(data.getData().getUserBankCardNo());
                        txBanlName.setText(data.getData().getBankName());
                        txBankAddres.setText(data.getData().getChildBankName());
                        if (data.getData().getGivenName() == null || data.getData().getSurname() == null) {
                            txName.setText(data.getData().getUserBankCardNo());
                        } else {
                            txName.setText(data.getData().getGivenName() + data.getData().getSurname());
                            edNote.setText(data.getData().getCode());
                        }
                        txMoney.setText(data.getData().getDealAmount().toString());
                        txOrderId.setText(data.getData().getDealOrderNo());

                        if (data.getData().getDealStatus().equals("1")) {
                            txType.setText(getResources().getString(R.string.tit2));
                        } else if (data.getData().getDealStatus().equals("2")) {
                            txType.setText(getResources().getString(R.string.tit3));
                        } else if (data.getData().getDealStatus().equals("3")) {
                            txType.setText(getResources().getString(R.string.tit4));
                        } else {
                            txType.setText(getResources().getString(R.string.tit5));
                        }
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {

        edNote.setText(remark);

        try {
            txOrderId.setText(TransactionNum);
            txMoney.setText(TransactionMoney);
            txName.setText(surname + givenName);
            txBankAddres.setText(BankAddress);
            txBanlName.setText(BankName);
            txBankNuber.setText(AccountNumber);
            txNote.setText(getResources().getString(R.string.tit) + remark + getResources().getString(R.string.tit1));
            if (Status.equals("1")) {
                txType.setText(getResources().getString(R.string.tit2));
            } else if (Status.equals("2")) {
                txType.setText(getResources().getString(R.string.tit3));
            } else if (Status.equals("3")) {
                txType.setText(getResources().getString(R.string.tit4));
            } else {
                txType.setText(getResources().getString(R.string.tit5));
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.tx_note, R.id.LL_tisi, R.id.tx_bankNuber, R.id.tx_banlName, R.id.tx_bankAddres, R.id.tx_Name, R.id.tx_Money, R.id.tx_OrderId, R.id.ed_note, R.id.LL_REAK, R.id.tx_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_note:
                break;
            case R.id.LL_tisi:
                break;
            case R.id.tx_bankNuber:
                break;
            case R.id.tx_banlName:
                break;
            case R.id.tx_bankAddres:
                break;
            case R.id.tx_Name:
                break;
            case R.id.tx_Money:
                break;
            case R.id.tx_OrderId:
                break;
            case R.id.ed_note:
                break;
            case R.id.LL_REAK:
                break;
            case R.id.tx_type:
                break;

            default:
                break;
        }
    }
}
