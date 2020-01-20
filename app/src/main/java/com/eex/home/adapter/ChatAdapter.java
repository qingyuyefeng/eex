package com.eex.home.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;
import com.eex.home.weight.Utils;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.util.CommonUtil;
import com.eex.home.activity.home.BuySellActivity;
import com.eex.home.activity.home.QRCodeActivity;
import com.eex.home.bean.History;
import com.eex.home.bean.OdrderId;
import com.eex.home.weight.HtmlUtils;
import com.eex.home.weight.MyDialog;
import com.tencent.mmkv.MMKV;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;


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
 * @Package: com.overthrow.home.adapter
 * @ClassName: ChatAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 12:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 12:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<History> mDatas;
    private TextView main_header_search_qingkong;
    private TextView header_title_name;
    private TextView time;
    private Button btn_Type1;
    private String type = "1";

    private boolean TYPE = true;
    private View view1;
    private MyDialog mMyDialog;
    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;
    private View mHeaderView;
    private OdrderId odrderIdvo;
    private TextView TxSee;
    private String OrderId1;

    private int contentSize;
    private List<History> mList;

    private MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);

    public ChatAdapter(Context context, List<History> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public int getContentSize() {
        return mDatas.size();
    }

    public void setHeaderView(OdrderId odrderIdvo1, String OrderId) {
        odrderIdvo = odrderIdvo1;
        OrderId1 = OrderId;
    }

    public void addItem(History msg) {
        mDatas.add(msg);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("mDatas", viewType + "----" + mDatas.get(0).getContent());

        //添加头文件
        if (mHeaderView == null && viewType == 0) {
            mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.rv_header, parent, false);
            mHeaderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeadHolder(mHeaderView);
        } else {
            //系统
            if (mDatas.get(viewType).getType() == 0) {
                View view2 = mLayoutInflater.inflate(R.layout.item_char, parent, false);
                view2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new ChatRightViewHolder1(view2);
            } else {//a796274a810d4fb1a2f05fa6dfd99d59
                Log.e("userIdtext", mDatas.get(viewType).getSendId() + "----" + "--" + contentSize);
                //显示右边
                if (mDatas.get(viewType).getSendId().equals(kv.decodeString("id"))) {
                    View view1 = mLayoutInflater.inflate(R.layout.item_chat_right, parent, false);
                    view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ChatRightViewHolder holder = new ChatRightViewHolder(view1);
                    return holder;
                } else {
                    View view1 = mLayoutInflater.inflate(R.layout.item_chat_left, parent, false);
                    view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ChatLeftViewHolder holder = new ChatLeftViewHolder(view1);
                    return holder;
                }
            }
        }

    }


    // 头部
    public class HeadHolder extends RecyclerView.ViewHolder {
        public HeadHolder(View itemView) {
            super(itemView);

            RelativeLayout header_return_RL = (RelativeLayout) itemView.findViewById(R.id.header_return_RL1);
            header_return_RL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                    Intent intent = new Intent();
                    intent.setClass(mContext.getApplicationContext(), BuySellActivity.class);
                    mContext.startActivity(intent);


                }
            });
            final CheckBox ckbType = (CheckBox) itemView.findViewById(R.id.ckbType);
            ckbType.setClickable(false);
            TxSee = (TextView) itemView.findViewById(R.id.TxSee);
            LinearLayout LLphone = (LinearLayout) itemView.findViewById(R.id.LLphone);
            LinearLayout headLinar = (LinearLayout) itemView.findViewById(R.id.head_linar);
            TextView txMoney = (TextView) itemView.findViewById(R.id.txMoney);
            btn_Type1 = (Button) itemView.findViewById(R.id.btn_Type);
            TextView oderId = (TextView) itemView.findViewById(R.id.oderId);
            TextView name = (TextView) itemView.findViewById(R.id.name);
            TextView nuberset = (TextView) itemView.findViewById(R.id.nuberset);
            TextView danPrice = (TextView) itemView.findViewById(R.id.danPrice);
            TextView Nuber = (TextView) itemView.findViewById(R.id.Nuber);
            TextView headeName = (TextView) itemView.findViewById(R.id.heade_name);
            TextView zhifuName = (TextView) itemView.findViewById(R.id.zhifu_name);
            TextView Rumark = (TextView) itemView.findViewById(R.id.Rumark);
            time = (TextView) itemView.findViewById(R.id.time);
            TextView name1 = (TextView) itemView.findViewById(R.id.name1);
            ImageView img_type = (ImageView) itemView.findViewById(R.id.img_type);
            LinearLayout LLType = (LinearLayout) itemView.findViewById(R.id.LLType);
            final LinearLayout LL_name = (LinearLayout) itemView.findViewById(R.id.LL_name);
            final LinearLayout LLmoney = (LinearLayout) itemView.findViewById(R.id.LLmoney);
            header_title_name = (TextView) itemView.findViewById(R.id.header_title_name);
            main_header_search_qingkong = (TextView) itemView.findViewById(R.id.main_header_search_qingkong);


            main_header_search_qingkong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view1 = mLayoutInflater.inflate(R.layout.dialog_c2cbuysell, null);
                    TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                    Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                    Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                    mMyDialog = new MyDialog(mContext, 0, 0, view1, R.style.DialogTheme);
                    mMyDialog.setCancelable(true);
                    tltle.setText("请您确认是否取消订单,当日取消订单数超过3次将会被限制当日的C2C交易");
                    mMyDialog.show();
                    btn_dimss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog.dismiss();
                        }
                    });
                    btn_sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CancelOrder();
                            mMyDialog.dismiss();
                        }
                    });
                }

            });

            LLphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view1 = mLayoutInflater.inflate(R.layout.dialog_buysell_phone, null);
                    Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                    TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                    tltle.setText(odrderIdvo.getPhone());
                    Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                    mMyDialog = new MyDialog(mContext, 0, 0, view1, R.style.DialogTheme);
                    mMyDialog.setCancelable(true);
                    mMyDialog.show();
                    btn_dimss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog.dismiss();
                        }
                    });
                    btn_sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//拨打电话
                            CommonUtil.call_phone(odrderIdvo.getPhone(), mContext);
                            mMyDialog.dismiss();
                        }
                    });
                }
            });
            TxSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("OrderId", OrderId1);
                    intent.setClass(mContext.getApplicationContext(), QRCodeActivity.class);
                    mContext.startActivity(intent);
                }
            });
            if (kv.decodeString("id").equals(odrderIdvo.getC2cOrderDetailDTO().getUserId())) {

                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 2) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("待确认收款");
                    btn_Type1.setText("待确认收款");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setClickable(false);
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                    if (data != null) {
                        data.cancel();
                    }
                    if (odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime() > 0) {
                        data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime(), 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                day = millisUntilFinished / (1000 * 60 * 60 * 24);//天
                                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//时
                                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);//分
                                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//秒
                                time.setText(Html.fromHtml("商家剩余确认收款时间,<font color=\"" + Color.RED + "\">" + hour + "时" + minute + "分" + second + "秒" + "</font> ,卖家逾期未确认平台自动将货币释放给买家"));
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        data.start();
                    }

                }
                //判断当前用户为买家
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 1) {
                    /////111111111111111111111111111111111
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("标记已付款");
                    btn_Type1.setText("标记已付款");
                    btn_Type1.setClickable(true);
                    TxSee.setVisibility(View.VISIBLE);
                    btn_Type1.setBackgroundResource(R.drawable.backbtn);
                    btn_Type1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View view1 = mLayoutInflater.inflate(R.layout.dialog_c2cbuysell, null);
                            TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                            Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                            Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                            mMyDialog = new MyDialog(mContext, 0, 0, view1, R.style.DialogTheme);
                            mMyDialog.setCancelable(true);
                            tltle.setText("请确认您是否已经汇款至卖家账户,等待卖家确认收款后即可释放货币");
                            mMyDialog.show();
                            btn_dimss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMyDialog.dismiss();
                                }
                            });
                            btn_sell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //
                                    isMoney();
                                    mMyDialog.dismiss();
                                }
                            });

                        }
                    });
                    if (data != null) {
                        data.cancel();
                    }
                    if (odrderIdvo.getC2cOrderDetailDTO().getPayEndTime() - odrderIdvo.getCurrentTime() > 0) {
                        data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getPayEndTime() - odrderIdvo.getCurrentTime(), 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                //天
                                day = millisUntilFinished / (1000 * 60 * 60 * 24);
                                //时
                                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                //分
                                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                                //秒
                                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                                time.setText(Html.fromHtml("待支付,请于<font color=\"#ff4329\">" + hour + "时" + minute + "分" + second + "秒" + "</font> 内向商家" + odrderIdvo.getUserName() + "支付<font color=\"#ff4329\">" + odrderIdvo.getC2cOrderDetailDTO().getPrice().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "CNY</font>购买" + odrderIdvo.getC2cOrderDetailDTO().getDealNum().stripTrailingZeros().toPlainString() + odrderIdvo.getC2cOrderDetailDTO().getCoinCode() + ",付款后及时标记为已付款."));
                            }

                            @Override
                            public void onFinish() {
                                time.setText(Html.fromHtml("待支付,请于<font color=\"#ff4329\">" + "00时00分00秒" + "</font> 内向商家" + odrderIdvo.getUserName() + "支付<font color=\"#ff4329\">" + odrderIdvo.getC2cOrderDetailDTO().getPrice().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "CNY</font>购买" + odrderIdvo.getC2cOrderDetailDTO().getDealNum().stripTrailingZeros().toPlainString() + odrderIdvo.getC2cOrderDetailDTO().getCoinCode() + ",付款后及时标记为已付款."));
                            }
                        };
                        data.start();
                    }

                }

                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 3) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已取消");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setText("已取消");
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 4) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已过期");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setText("已过期");
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 5) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已完成");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setText("已完成");
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }
            } else {//卖家
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 1) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("待付款");
                    btn_Type1.setText("待付款");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setClickable(false);
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                    if (data != null) {
                        data.cancel();
                    }
                    if (odrderIdvo.getC2cOrderDetailDTO().getPayEndTime() - odrderIdvo.getCurrentTime() > 0) {
                        data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getPayEndTime() - odrderIdvo.getCurrentTime(), 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                day = millisUntilFinished / (1000 * 60 * 60 * 24);//天
                                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//时
                                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);//分
                                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//秒
                                time.setText(Html.fromHtml("买家将于,<font color=\"" + Color.RED + "\">" + hour + "时" + minute + "分" + second + "秒" + "</font> 内向您支付<font color=\"" + Color.RED + "\">" + (odrderIdvo.getC2cOrderDetailDTO().getDealNum().multiply(odrderIdvo.getC2cOrderDetailDTO().getUnitPrice()).setScale(2, BigDecimal.ROUND_DOWN)) + "CNY</font>购买" + odrderIdvo.getC2cOrderDetailDTO().getDealNum().stripTrailingZeros().toPlainString() + odrderIdvo.getC2cOrderDetailDTO().getCoinCode() + "确认收款后请标记为已收款"));
                            }

                            @Override
                            public void onFinish() {
                                time.setText(Html.fromHtml("买家将于<font color=\"" + Color.RED + "\">" + "00时" + "00分" + "00秒</font>内向您支付<font color=\"" + Color.RED + "\">" + (odrderIdvo.getC2cOrderDetailDTO().getDealNum().multiply(odrderIdvo.getC2cOrderDetailDTO().getUnitPrice()).setScale(2, BigDecimal.ROUND_DOWN)) + "CNY</font>购买" + odrderIdvo.getC2cOrderDetailDTO().getDealNum().stripTrailingZeros().toPlainString() + odrderIdvo.getC2cOrderDetailDTO().getCoinCode() + "确认收款后请标记为已收款"));
                            }
                        };
                        data.start();
                    }

                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 2) {
                    main_header_search_qingkong.setVisibility(View.VISIBLE);
                    header_title_name.setText("待确认收款");
                    btn_Type1.setText("待确认收款");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setClickable(true);
                    btn_Type1.setBackgroundResource(R.drawable.backbtn);
                    btn_Type1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View view1 = mLayoutInflater.inflate(R.layout.dialog_trade_password, null);
                            TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                            EditText TradeEd = (EditText) view1.findViewById(R.id.Trade_ed);
                            Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                            Button TradeSell = (Button) view1.findViewById(R.id.Trade_sell);
                            mMyDialog = new MyDialog(mContext, 0, 0, view1, R.style.DialogTheme);
                            mMyDialog.setCancelable(true);
                            tltle.setText("您确认已经收到买家的汇款吗?一旦确认收款将会立即将锁定的货币释放给买家");
                            mMyDialog.show();
                            btn_dimss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMyDialog.dismiss();
                                }
                            });
                            TradeSell.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("CheckResult")
                                @Override
                                public void onClick(View v) {

                                    if (TradeEd.getText().toString().trim() != null) {

                                        HashMap<String, String> requestParam = new HashMap<>();
                                        requestParam.put("id", odrderIdvo.getC2cOrderDetailDTO().getId());
                                        requestParam.put("accountPassWord", Utils.md5(TradeEd.getText().toString().trim() + "hello, moto"));
                                        ApiFactory.getInstance()
                                                .updatePayOK(kv.decodeString("tokenId"), requestParam)
                                                .compose(RxSchedulers.io_main())
                                                .subscribe(data1 -> {

                                                    if (data1.isStauts()) {
                                                        if (data != null) {
                                                            data.cancel();
                                                        }
                                                        main_header_search_qingkong.setVisibility(View.GONE);
                                                        btn_Type1.setText("待确认收款");
                                                        TxSee.setVisibility(View.GONE);
                                                        btn_Type1.setClickable(false);
                                                        btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                                                        time.setText("已完成");
                                                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                                                        mMyDialog.dismiss();

                                                    } else {
                                                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();

                                                }, throwable -> {

                                                });


                                    } else {
                                        Toast.makeText(mContext, "请输入交易密码", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }
                    });
                    if (data != null) {
                        data.cancel();
                    }
                    if (odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime() > 0) {
                        data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime(), 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                //天
                                day = millisUntilFinished / (1000 * 60 * 60 * 24);
                                //时
                                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                //分
                                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                                //秒
                                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                                time.setText(Html.fromHtml("买家已付款，请您在<font color=\"#ff4329\">" + hour + "时" + minute + "分" + second + "秒</font> 内确认收款，超时将自动把货币释放给买家"));
                            }

                            @Override
                            public void onFinish() {
                                time.setText(Html.fromHtml("买家已付款，请您在<font color=\"#ff4329\">" + "00时00分00秒" + "</font> 内确认收款，超时将自动把货币释放给买家"));
                            }
                        };
                        data.start();
                    }

                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 3) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已取消");
                    btn_Type1.setText("已取消");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 4) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已过期");
                    btn_Type1.setText("已过期");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }
                if (odrderIdvo.getC2cOrderDetailDTO().getState() == 5) {
                    main_header_search_qingkong.setVisibility(View.GONE);
                    header_title_name.setText("已完成");
                    btn_Type1.setText("已完成");
                    TxSee.setVisibility(View.GONE);
                    btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                }

            }
            txMoney.setText(odrderIdvo.getC2cOrderDetailDTO().getPrice().stripTrailingZeros().toPlainString() + "CNY");
            img_type.setVisibility(View.GONE);
            oderId.setText(odrderIdvo.getC2cOrderDetailDTO().getOrderNo());
            name1.setText(odrderIdvo.getUserName());
            name.setText(odrderIdvo.getUserName());
            nuberset.setText("30日成单量:" + odrderIdvo.getDealNum30Day());
            danPrice.setText(odrderIdvo.getC2cOrderDetailDTO().getUnitPrice().stripTrailingZeros().toPlainString() + "CNY");
            Nuber.setText(odrderIdvo.getC2cOrderDetailDTO().getDealNum().stripTrailingZeros().toPlainString() + odrderIdvo.getC2cOrderDetailDTO().getCoinCode());
            if (odrderIdvo.getUserType() == 1) {
                headLinar.setVisibility(View.VISIBLE);
                headeName.setText(odrderIdvo.getRealName() + "");
            }
            zhifuName.setText(odrderIdvo.getC2cOrderDetailDTO().getPayType());
            Rumark.setText(odrderIdvo.getC2cOrderDetailDTO().getRemark());
            LLType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TYPE) {
                        LL_name.setVisibility(View.VISIBLE);
                        LLmoney.setVisibility(View.VISIBLE);
                        TYPE = false;
                        ckbType.setChecked(true);
                    } else {
                        LL_name.setVisibility(View.GONE);
                        LLmoney.setVisibility(View.GONE);
                        TYPE = true;
                        ckbType.setChecked(false);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        History msg = mDatas.get(position);
        String time = msg.getCreateTime();
        String content = msg.getContent();
        Log.e("NewChartActivityItem", time + content + "----");
        if (holder instanceof ChatLeftViewHolder) {
            ((ChatLeftViewHolder) holder).mTvLeftTime.setText(time);
            ((ChatLeftViewHolder) holder).mTvMsgLeft.setText(HtmlUtils.getHtml(mContext, ((ChatLeftViewHolder) holder).mTvMsgLeft, "<div>" + content + "</div>"));

        } else if (holder instanceof ChatRightViewHolder) {
            ((ChatRightViewHolder) holder).mTvRightTime.setText(time);
            ((ChatRightViewHolder) holder).mTvMsgRight.setText(HtmlUtils.getHtml(mContext, ((ChatRightViewHolder) holder).mTvMsgRight, "<div>" + content + "</div>"));
        } else if (holder instanceof ChatRightViewHolder1) {
            ((ChatRightViewHolder1) holder).xitongTime.setText(time);
            ((ChatRightViewHolder1) holder).webxitong.setText(HtmlUtils.getHtml(mContext, ((ChatRightViewHolder1) holder).webxitong, "<div>" + content + "</div>"));

        }

    }

    @Override
    public int getItemViewType(int position) {
        contentSize = position;
        Log.e("contentSize", mDatas.get(position).getMsgType() + "------");
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class ChatLeftViewHolder extends RecyclerView.ViewHolder {
        TextView mTvLeftTime = (TextView) itemView.findViewById(R.id.tv_left_time);
        TextView mTvMsgLeft = (TextView) itemView.findViewById(R.id.tv_msg_left);

        ChatLeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ChatRightViewHolder extends RecyclerView.ViewHolder {
        TextView mTvRightTime = (TextView) itemView.findViewById(R.id.tv_right_time);
        TextView mTvMsgRight = (TextView) itemView.findViewById(R.id.tv_msg_right);

        ChatRightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ChatRightViewHolder1 extends RecyclerView.ViewHolder {
        TextView webxitong = (TextView) itemView.findViewById(R.id.webxitong);
        TextView xitongTime = (TextView) itemView.findViewById(R.id.xitongTime);

        ChatRightViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 确认付款
     */
    @SuppressLint("CheckResult")
    private void isMoney() {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", odrderIdvo.getC2cOrderDetailDTO().getId());
        ApiFactory.getInstance()
                .updatePayBeConfirm(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data1 -> {
                    if (data1.isStauts()) {
                        if (odrderIdvo.getC2cOrderDetailDTO().getState() == 1) {
                            main_header_search_qingkong.setVisibility(View.GONE);
                            header_title_name.setText("待确认收款");
                            btn_Type1.setText("待确认收款");
                            TxSee.setVisibility(View.GONE);
                            btn_Type1.setClickable(false);
                            btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                            if (data != null) {
                                data.cancel();
                            }
                            if (odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime() > 0) {
                                data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime(), 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        //天
                                        day = millisUntilFinished / (1000 * 60 * 60 * 24);
                                        //时
                                        hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                        //分
                                        minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                                        //秒
                                        second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                                        time.setText(Html.fromHtml("商家剩余确认收款时间,<font color=\"" + Color.RED + "\">" + hour + "时" + minute + "分" + second + "秒" + "</font> ,卖家逾期未确认平台自动将货币释放给买家"));
                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                };
                                data.start();
                            }
                        } else {
                            main_header_search_qingkong.setVisibility(View.VISIBLE);
                            header_title_name.setText("标记已收款");
                            btn_Type1.setText("标记已收款");
                            TxSee.setVisibility(View.GONE);
                            btn_Type1.setClickable(true);
                            btn_Type1.setBackgroundResource(R.drawable.backbtn);
                            btn_Type1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    View view1 = mLayoutInflater.inflate(R.layout.dialog_trade_password, null);
                                    TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                                    EditText TradeEd = (EditText) view1.findViewById(R.id.Trade_ed);
                                    Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                                    Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                                    mMyDialog = new MyDialog(mContext, 0, 0, view1, R.style.DialogTheme);
                                    mMyDialog.setCancelable(true);
                                    tltle.setText("您确认已经收到买家的汇款吗?一旦确认收款将会立即将锁定的货币释放给买家");
                                    mMyDialog.show();
                                    btn_dimss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mMyDialog.dismiss();
                                        }
                                    });
                                    btn_sell.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            if (TradeEd.getText().toString().trim() != null) {

                                                HashMap<String, String> requestParam = new HashMap<>();
                                                requestParam.put("id", odrderIdvo.getC2cOrderDetailDTO().getId());
                                                requestParam.put("accountPassWord", Utils.md5(TradeEd.getText().toString().trim() + "hello, moto"));
                                                ApiFactory.getInstance()
                                                        .updatePayOK(kv.decodeString("tokenId"), requestParam)
                                                        .compose(RxSchedulers.io_main())
                                                        .subscribe(data1 -> {

                                                            if (data1.isStauts()) {
                                                                if (data != null) {
                                                                    data.cancel();
                                                                }
                                                                main_header_search_qingkong.setVisibility(View.GONE);
                                                                btn_Type1.setText("待确认收款");
                                                                TxSee.setVisibility(View.GONE);
                                                                btn_Type1.setClickable(false);
                                                                btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                                                                time.setText("已完成");
                                                                Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                                                                mMyDialog.dismiss();

                                                            } else {
                                                                Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                                                            }
                                                            Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();

                                                        }, throwable -> {

                                                        });


                                            } else {
                                                Toast.makeText(mContext, "请输入交易密码", Toast.LENGTH_SHORT).show();
                                            }


//                                            isShpuMoney();//确认收款


                                            mMyDialog.dismiss();
                                        }
                                    });


                                }
                            });
                            if (data != null) {
                                data.cancel();
                            }
                            if (odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime() > 0) {
                                data = new CountDownTimer(odrderIdvo.getC2cOrderDetailDTO().getConfirmTime() - odrderIdvo.getCurrentTime(), 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        //天
                                        day = millisUntilFinished / (1000 * 60 * 60 * 24);
                                        //时
                                        hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                        //分
                                        minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                                        //秒
                                        second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                                        time.setText(Html.fromHtml("买家已付款，请您在<font color=\"#ff4329\">" + hour + "时" + minute + "分" + second + "秒</font> 内确认收款，超时将自动把货币释放给买家"));
                                    }

                                    @Override
                                    public void onFinish() {
                                        time.setText(Html.fromHtml("买家已付款，请您在<font color=\"#ff4329\">" + "00时00分00秒" + "</font> 内确认收款，超时将自动把货币释放给买家"));
                                    }
                                };
                                data.start();
                            }
                        }

                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    private String getTime() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        return date;
    }

    /**
     * 取人收款
     */
    @SuppressLint("CheckResult")
    private void isShpuMoney() {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", odrderIdvo.getC2cOrderDetailDTO().getId());
        ApiFactory.getInstance()
                .updatePayOK(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data1 -> {
                    if (data1.isStauts()) {
                        if (data != null) {
                            data.cancel();
                        }
                        main_header_search_qingkong.setVisibility(View.GONE);
                        btn_Type1.setText("待确认收款");
                        TxSee.setVisibility(View.GONE);
                        btn_Type1.setClickable(false);
                        btn_Type1.setBackgroundResource(R.drawable.btn_grd);
                        time.setText("已完成");
                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }

    @SuppressLint("CheckResult")
    private void CancelOrder() {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", OrderId1);

        ApiFactory.getInstance()
                .updatePayCancle(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data1 -> {
                    if (data1.isStauts()) {
                        main_header_search_qingkong.setClickable(false);
                        main_header_search_qingkong.setText("已取消");
                        btn_Type1.setClickable(false);
                        btn_Type1.setText("已取消");
                        data.cancel();
                        time.setText("订单已取消");
                        btn_Type1.setBackgroundResource(R.drawable.btn_grd);

                    } else {
                        Toast.makeText(mContext, data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }
}
