package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.PhoneNameActivity;
import com.eex.home.activity.home.RealNameActivity;
import com.eex.home.weight.MyDialog;
import java.util.HashMap;

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
 * @ClassName: SetActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 16:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 16:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 安全中心
 */
public class SetActivity extends BaseActivity {

  /**
   *
   */
  @BindView(R.id.comtitlebar)
  ComTitleBar comtitlebar;
  /**
   *
   */
  @BindView(R.id.txName)
  TextView txName;
  /**
   *
   */
  @BindView(R.id.set_SM_LL)
  LinearLayout setSMLL;
  /**
   *
   */
  @BindView(R.id.tx_isPnone)
  TextView txIsPnone;
  /**
   *
   */
  @BindView(R.id.phone_LL)
  LinearLayout phoneLL;
  /**
   *
   */
  @BindView(R.id.set_SJRZ_LL)
  LinearLayout setSJRZLL;
  /**
   *
   */
  @BindView(R.id.tx_isgoogle)
  TextView txIsgoogle;
  /**
   *
   */
  @BindView(R.id.Goole_LL)
  LinearLayout GooleLL;
  /**
   *
   */
  @BindView(R.id.set_Goole_LL)
  LinearLayout setGooleLL;
  /**
   *
   */
  @BindView(R.id.txEmail)
  TextView txEmail;
  /**
   *
   */
  @BindView(R.id.Emal_LL)
  LinearLayout EmalLL;
  /**
   *
   */
  @BindView(R.id.set_Emal_LL)
  LinearLayout setEmalLL;
  /**
   *
   */
  @BindView(R.id.set_XGMM_LL)
  LinearLayout setXGMMLL;
  /**
   *
   */
  @BindView(R.id.txjiaoyi)
  TextView txjiaoyi;
  /**
   *
   */
  @BindView(R.id.MOneyPword_LL)
  LinearLayout MOneyPwordLL;
  /**
   *
   */
  @BindView(R.id.set_MOneyPword_LL)
  LinearLayout setMOneyPwordLL;
  /**
   *
   */
  @BindView(R.id.textView2)
  TextView textView2;

  private MyDialog mMyDialogphone;
  private MyDialog mMyDialog1;

  private View view;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_set;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    comtitlebar.setTitle("安全中心");

    getId();

    isSM();
  }

  public void getId() {

    if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord") == "") {
      txjiaoyi.setText("设置交易密码");
    } else {
      txjiaoyi.setText("重置交易密码");
    }

    if (kv.decodeInt("googleState") == 0) {
      txIsgoogle.setText("未认证");
    } else {
      txIsgoogle.setText("已认证");
    }

    try {
      if (kv.decodeString("email").equals("")) {
        txEmail.setText("未认证");
      } else {
        txEmail.setText("已认证");
      }
    } catch (Exception e) {
      txEmail.setText("未认证");
    }

    try {
      if (kv.decodeString("tokenId") != null) {
        if (kv.decodeString("phone").equals("")) {
          txIsPnone.setText("未认证");
        } else {
          txIsPnone.setText("已认证");
        }
      }
    } catch (Exception e) {
      txIsPnone.setText("未认证");
    }

    try {

      if (kv.decodeInt("realName") != 1) {
        txName.setText("已实名");
      } else {
        txName.setText("未实名");
      }
    } catch (Exception e) {
      txName.setText("未实名");
    }
  }

  private void isSM() {
    if (kv.decodeString("tokenId") != null) {
      DataSM();
    } else {
      txName.setText("未实名");
    }
  }

  /**
   *
   */
  @SuppressLint("CheckResult")
  private void DataSM() {
    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          try {
            if (data.getData().getLevel().equals(0)) {
              txName.setText(getActivity().getResources().getString(R.string.sh1));
              setSMLL.setEnabled(false);
            } else if (data.getData().getLevel().equals(1)) {
              txName.setText(getActivity().getResources().getString(R.string.sh2));
              setSMLL.setEnabled(false);
            } else {
              txName.setText(getActivity().getResources().getString(R.string.sh3));
            }
          } catch (Exception e) {

            txName.setText(getActivity().getResources().getString(R.string.sh3));
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

  @OnClick({
      R.id.comtitlebar, R.id.txName, R.id.set_SM_LL, R.id.tx_isPnone, R.id.phone_LL,
      R.id.set_SJRZ_LL, R.id.tx_isgoogle, R.id.Goole_LL, R.id.set_Goole_LL, R.id.txEmail,
      R.id.Emal_LL, R.id.set_Emal_LL, R.id.set_XGMM_LL, R.id.txjiaoyi, R.id.MOneyPword_LL,
      R.id.set_MOneyPword_LL, R.id.textView2
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.comtitlebar:
        finish();
        break;
      case R.id.txName:
        break;
      //实名
      case R.id.set_SM_LL:
        intent.setClass(SetActivity.this, RealNameActivity.class);
        startActivity(intent);
        break;
      case R.id.tx_isPnone:
        break;
      //手机认证
      case R.id.phone_LL:
        intent.setClass(SetActivity.this, PhoneNameActivity.class);
        startActivity(intent);
        break;

      case R.id.set_SJRZ_LL:

        break;
      case R.id.tx_isgoogle:
        break;
      //google认证
      case R.id.Goole_LL:
        intent.setClass(SetActivity.this, GooleActivity.class);
        startActivity(intent);
        break;
      case R.id.set_Goole_LL:
        break;
      case R.id.txEmail:
        break;
      //邮箱认证

      case R.id.Emal_LL:
        intent.setClass(SetActivity.this, EmailActivity.class);
        startActivity(intent);
        break;
      case R.id.set_Emal_LL:
        break;
      //修改密码
      case R.id.set_XGMM_LL:
        intent.setClass(SetActivity.this, NewPasswordActivity.class);
        startActivity(intent);
        break;
      case R.id.txjiaoyi:
        break;
      case R.id.MOneyPword_LL:
        //是否实名
        IsName();
        break;
      case R.id.set_MOneyPword_LL:
        //是否实名
        IsName();
        break;
      case R.id.textView2:
        break;
      default:
        break;
    }
  }

  @SuppressLint("CheckResult")
  private void IsName() {
    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          try {
            if (data.getData().getLevel().equals(1)) {
              view = getLayoutInflater().inflate(R.layout.dialog_2cbuysell, null);
              TextView tltle = (TextView) view.findViewById(R.id.tltle);
              TextView btn_dimss = (Button) view.findViewById(R.id.btn_dimss);
              TextView btn_sell = (Button) view.findViewById(R.id.btn_sell);
              mMyDialog1 = new MyDialog(SetActivity.this, 0, 0, view, R.style.DialogTheme);
              mMyDialog1.setCancelable(true);
              tltle.setText("请完成实名等级2认证");
              mMyDialog1.show();
              btn_dimss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mMyDialog1.dismiss();
                }
              });
              btn_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent intent = new Intent();
                  intent.setClass(SetActivity.this, RealNameActivity.class);
                  startActivity(intent);
                  mMyDialog1.dismiss();
                }
              });
            } else if (data.getData().getLevel().equals(2)) {

              if (kv.decodeString("phone") != null && kv.decodeString("phone") != "") {
                Intent intent = new Intent();
                intent.setClass(SetActivity.this, MeMoneyPwordActivity.class);
                startActivityForResult(intent, 2000);
              } else {
                mMyDialogphone = new MyDialog(SetActivity.this, 0, 0, view, R.style.DialogTheme);
                TextView tltle1 = (TextView) view.findViewById(R.id.tltle);
                Button btn_dimss1 = (Button) view.findViewById(R.id.btn_dimss);
                Button btn_sell1 = (Button) view.findViewById(R.id.btn_sell);
                mMyDialogphone.setCancelable(true);
                tltle1.setText("请绑定手机号后交易");
                mMyDialogphone.show();
                btn_dimss1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    mMyDialogphone.dismiss();
                  }
                });
                btn_sell1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(SetActivity.this, PhoneNameActivity.class);
                    startActivity(intent);
                    mMyDialogphone.dismiss();
                  }
                });
              }
            } else if (data.getData().getLevel().equals(3)) {
              if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                if (kv.decodeString("phone") != null && kv.decodeString("phone") != "") {
                  intent.setClass(SetActivity.this, MeMoneyPwordActivity.class);
                  startActivityForResult(intent, 2000);
                } else {
                  Toast.makeText(SetActivity.this, "请绑定手机后操作", Toast.LENGTH_SHORT).show();
                }
              } else {
                mMyDialogphone = new MyDialog(SetActivity.this, 0, 0, view, R.style.DialogTheme);
                TextView tltle = (TextView) view.findViewById(R.id.tltle);
                Button btn_dimss = (Button) view.findViewById(R.id.btn_dimss);
                Button btn_sell = (Button) view.findViewById(R.id.btn_sell);
                mMyDialogphone.setCancelable(true);
                tltle.setText("请绑定手机号后交易");
                mMyDialogphone.show();
                btn_dimss.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    mMyDialogphone.dismiss();
                  }
                });
                btn_sell.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                    intent.setClass(SetActivity.this, PhoneNameActivity.class);
                    startActivity(intent);
                    mMyDialogphone.dismiss();
                  }
                });
              }
            }
          } catch (Exception e) {

          }
        }, throwable -> {

        });
  }

  @Override
  protected void onResume() {
    super.onResume();

    getId();
    DataSM();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 2000) {
      txjiaoyi.setText("重置交易密码");
    }
  }
}
