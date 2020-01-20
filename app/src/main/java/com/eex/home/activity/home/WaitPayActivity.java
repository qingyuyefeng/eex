package com.eex.home.activity.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.SwipeUtil;

import com.eex.compat.BuildConfig;
import com.eex.home.adapter.ChatAdapter;
import com.eex.home.bean.History;
import com.eex.home.bean.OdrderId;
import com.eex.home.weight.ActionSheetDialog;
import com.eex.home.weight.MyDialog;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;


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
 * @ClassName: WaitPayActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WaitPayActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.imgButton)
    ImageView imgButton;
    /**
     *
     */
    @BindView(R.id.edtData)
    EditText edtData;
    /**
     *
     */
    @BindView(R.id.TextViewData)
    ImageView TextViewData;
    /**
     *
     */
    @BindView(R.id.txet)
    LinearLayout txet;
    /**
     *
     */
    @BindView(R.id.c2cRecView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private String OrderId = "";

    private int pageNo = 1;
    private int pageSize = 200;

    private ArrayList<History> list = new ArrayList<>();
    private ArrayList<History> list1 = new ArrayList<>();
    private OdrderId odrderIdvo;

    private ChatAdapter adapter;
    public Map<String, String> map = new HashMap();
    private WebSocketClient mWebSocketClient;
    private String WebSocketType = "1";

    /**
     * 调用照相机返回图片文件
     */
    private File tempFile;
    private String path;
    private boolean TYPE = true;
    /**
     * 相册请求码
     */
    private static final int ALBUM_REQUEST_CODE = 1;
    /**
     * 相机请求码
     */
    private static final int CAMERA_REQUEST_CODE = 2;
    /**
     * 剪裁请求码
     */
    private static final int CROP_REQUEST_CODE = 3;

    private Uri uritempFile;


    private View view1;
    private Button btnDimss;
    private Button btnSell;
    private MyDialog mMyDialog;

    private CountDownTimer data;

    private TextView main_header_search_qingkong;
    private Button btn_Type1;
    private TextView time;
    private TextView header_title_name;
    private long day;
    private long hour;
    private long minute;
    private long second;
    private TextView TxSee;

    private TextView txMoney;
    private TextView oderId;
    private TextView name;
    private TextView nuberset;
    private TextView danPrice;
    private TextView Nuber;
    private TextView Rumark;
    private TextView name1;
    private ImageView img_type;
    private LinearLayout LLType;
    private LinearLayout LL_name;
    private LinearLayout LLmoney;

    private CheckBox ckbType;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                String news = "";
                news = msg.getData().getString("news");
                History chat = CommonUtil.fromJson(news, History.class);
                if (chat.getOrderId() != null) {
                    if (chat.getOrderId().equals(OrderId)) {
                        try {
                            adapter.addItem(chat);
                            recyclerView.setFocusable(true);
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                            recyclerView.setNestedScrollingEnabled(false);

                        } catch (Exception e) {

                        }
                    }
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_pay;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (getIntent().getStringExtra("OrderId") != null) {
            OrderId = getIntent().getStringExtra("OrderId");
        }

        //获取订单详情
        getChatLsit();

        try {
            //长连接获取最新消息
            initSocketClient();
            mWebSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取订单详情
     */
    @SuppressLint("CheckResult")
    private void getChatLsit() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", OrderId);
        ApiFactory.getInstance()
                .getOrderDetailById(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    odrderIdvo = data.getData();
                    if (data.getData() != null) {
                        //获取聊天记录
                        getChatLsitData();
                    } else {

                    }
                }, throwable -> {

                });

//        HashMap<String, String> requestParam = new HashMap<>();
//        requestParam.put("id", OrderId);
//        ApiFactory.getInstance()
//                .getOrderDetailById(UserUtil.getTokenId(), requestParam)
//                .compose(RxSchedulers.io_main())
//                .subscribe(new Consumer<Data<OdrderId>>() {
//                    @Override
//                    public void accept(Data<OdrderId> data) throws Exception {
//                        SwipeUtil.loadCompleted(swipeRefresh);
//                        Log.d(TAG, "打印数据 = data  " + data.toString());
//                        if (data.getCode() == 200) {
//                            odrderIdvo = data.getData();
//                            Log.d(TAG, "打印数据1111 = odrderIdvo  " + odrderIdvo);
//                            adapter.setHeaderView(odrderIdvo, OrderId);
//                            //获取聊天记录
//                            getChatLsitData();
//                        } else {
//
//                        }
//
//                        //这个改成同步执行
//                        Log.e(TAG, "accept: " + "还是会抛异常的");
//                    }
//
//                } , new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        SwipeUtil.loadCompleted(swipeRefresh);
//                        Log.e(TAG, "accept: " + "只会执行这里");
//                    }
//                });


    }

    @SuppressLint("CheckResult")
    private void getChatLsitData() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("pageSize", pageSize + "");
        requestParam.put("pageNo", pageNo + "");
        requestParam.put("msgType", "1");
        requestParam.put("createTime", getTime());
        requestParam.put("orderId", OrderId);


        ApiFactory.getInstance()
                .getHistory(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (data.getData() != null) {
                        if (data.getData().getPageNo() > 1) {
                            list1.addAll(data.getData().getResultList());
                        } else {
                            if (data.getData().getResultList() != null && data.getData().getResultList().size() != 0) {

                                list.addAll(data.getData().getResultList());
                                History newDATA = new History();
                                newDATA.setContent(list.get(0).getContent());
                                newDATA.setCreateTime(list.get(0).getCreateTime());
                                newDATA.setMsgType(list.get(0).getMsgType());
                                newDATA.setType(list.get(0).getType());
                                newDATA.setSendId(list.get(0).getSendId());
                                newDATA.setIsRead(list.get(0).getIsRead());
                                newDATA.setOrderId(list.get(0).getOrderId());
                                newDATA.setSendName(list.get(0).getSendName());
                                newDATA.setReceiveName(list.get(0).getReceiveName());
                                // 倒序排列
                                Collections.reverse(list);
                                list.add(0, newDATA);
                                pageNo = 1;
                            }
                            setView();
                        }
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    private void setView() {

        adapter = new ChatAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setHasStableIds(true);
        adapter.setHeaderView(odrderIdvo, OrderId);
        recyclerView.setAdapter(adapter);
//        addHeaderView();

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
     * 长连接获取最新消息
     */
    private void initSocketClient() throws URISyntaxException {
        if (mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(new URI(WPConfig.INSTANCE.getWSUrl() + "chatSocket/" + kv.decodeString("tokenId"))) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //连接成功
                    Log.e("LOG", "opened connection");
                    WebSocketType = "1";
                }

                @Override
                public void onMessage(final String s) {
                    //服务端消息来了

                    Message msg = Message.obtain();
                    msg.what = 0x111;
                    Bundle bundle = new Bundle();
                    bundle.putString("news", s);
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }

                @Override
                public void onClose(int i, String s, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    WebSocketType = "2";
                    Log.e("LOG", "Connection closed by " + (remote ? "remote peer" : "us") + ", info=" + s);
                    //
                    closeConnect();
                }

                @Override
                public void onError(Exception e) {
                    WebSocketType = "2";
                    Log.e("LOG", "error:" + e);
                }
            };
        }
    }

    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            mWebSocketClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWebSocketClient = null;
        }
    }

    /**
     * 向服务器发送消息的方法
     */
    private void sendMsg(final String msg) {
        if (mWebSocketClient == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //长连接获取最新消息
                        initSocketClient();
                        mWebSocketClient.connect();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            map.put("type", odrderIdvo.getUserType() + "");
            map.put("code", odrderIdvo.getUserType() + "");
            if (odrderIdvo.getUserType() == 1) {
                map.put("receiveId", odrderIdvo.getC2cOrderDetailDTO().getUserId());
            } else if (odrderIdvo.getUserType() == 2) {
                map.put("receiveId", odrderIdvo.getC2cOrderDetailDTO().getSellId());
            }
            map.put("msgType", "1");
            map.put("orderId", OrderId);
            map.put("content", msg);
            map.put("createTime", getTime());

            mWebSocketClient.send(CommonUtil.toJson(map));
        }


    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgButton, R.id.edtData, R.id.TextViewData, R.id.txet,
//            R.id.TxSee, R.id.LLphone, R.id.header_return_RL1, R.id.main_header_search_qingkong
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {

//            case R.id.TxSee:
//                Intent intent = new Intent();
//                intent.putExtra("OrderId", OrderId);
//                intent.setClass(WaitPayActivity.this, QRCodeActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.LLphone:
//                view1 = getLayoutInflater().inflate(R.layout.dialog_buysell_phone, null);
//                btnDimss = (Button) view1.findViewById(R.id.btn_dimss);
//                TextView tltle = (TextView) view1.findViewById(R.id.tltle);
//                tltle.setText(odrderIdvo.getPhone());
//                btnSell = (Button) view1.findViewById(R.id.btn_sell);
//                mMyDialog = new MyDialog(WaitPayActivity.this, 0, 0, view1, R.style.DialogTheme);
//                mMyDialog.setCancelable(true);
//                mMyDialog.show();
//                btnDimss.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mMyDialog.dismiss();
//                    }
//                });
//                btnSell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {//拨打电话
//                        CommonUtil.call_phone(odrderIdvo.getPhone(),WaitPayActivity.this);
//                        mMyDialog.dismiss();
//                    }
//                });
//                break;
//            case R.id.header_return_RL1:
//                finish();
//                break;
//
//                //取消订单
//            case R.id.main_header_search_qingkong:
//                View   view1    = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
//                TextView   tltle1  = (TextView) view1.findViewById(R.id.tltle);
//                Button  btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
//                Button  btn_sell = (Button) view1.findViewById(R.id.btn_sell);
//                mMyDialog = new MyDialog(WaitPayActivity.this, 0, 0, view1, R.style.DialogTheme);
//                mMyDialog.setCancelable(true);
//                tltle1.setText("请您确认是否取消订单,当日取消订单数超过3次将会被限制当日的C2C交易");
//                mMyDialog.show();
//                btn_dimss.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mMyDialog.dismiss();
//                    }
//                });
//                btn_sell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CancelOrder();
//                        mMyDialog.dismiss();
//                    }
//                });
//                break;
            case R.id.imgButton:

                new ActionSheetDialog(WaitPayActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(getResources().getString(R.string.xc),
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {

                                    @Override
                                    public void onClick(int which) {
                                        //相册
                                        getPicFromAlbm();

                                    }
                                })
                        .addSheetItem(getResources().getString(R.string.xj),
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {

                                    @Override
                                    public void onClick(int which) {
                                        //相机
                                        checkPermission();
                                    }
                                }).show();
                break;
            case R.id.edtData:

                View decorView = getActivity().getWindow().getDecorView();
                View contentView = getActivity().findViewById(Window.ID_ANDROID_CONTENT);
                decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));

                break;
            case R.id.TextViewData:
                try {
                    if (!edtData.getText().toString().trim().equals("")) {
                        sendMsg(edtData.getText().toString().trim());
                        edtData.setText("");
                    }
                } catch (Exception e) {
                        
                }
                break;
            case R.id.txet:
                break;


            default:
                break;
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(View decorView, View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getRootView().getHeight();
                int diff = height - r.bottom;
                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };

    }

    @SuppressLint("CheckResult")
    private void CancelOrder() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", OrderId);

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
                        Toast.makeText(getActivity(), data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });
    }


    /**
     * 相册
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);

    }


    /**
     * 检查拍照权限
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(WaitPayActivity.this, Manifest.permission.CAMERA)) {
                // 提示已经禁止
                Toast.makeText(WaitPayActivity.this, getResources().getString(R.string.quanxian), Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(WaitPayActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
            }
        } else {
            getPicFromCamera();
        }
    }

    private void getPicFromCamera() {

        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //判断版本
        //如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(WaitPayActivity.this, BuildConfig.AUTHORITY, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //调用相机后返回
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(WaitPayActivity.this, BuildConfig.AUTHORITY, tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            //调用相册后返回
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.e("CODE", data.getData().toString());
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:
                //调用剪裁后返回
                try {
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));

                    path = saveImage("crop3", image);
                    PutOne(path, 3, image);
                    Log.e("path", path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;

        }
    }

    /**
     * 上传图片
     *
     * @param path
     * @param i
     * @param image
     */
    @SuppressLint("CheckResult")
    private void PutOne(String path, int i, Bitmap image) {

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiFactory.getInstance()
                .uploadFile(kv.decodeString("tokenId"), filePart)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    sendMsg("<img src ='" + WPConfig.Chart + data.getData().toString() + "' />");
                    if (data.isStauts() == true) {
                        t(data.getMsg());

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });
    }

    /**
     * 裁剪图片
     *
     * @param crop3
     * @param image
     * @return
     */
    private String saveImage(String crop3, Bitmap image) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = crop3 + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 裁剪图片
     *
     * @param contentUri
     */
    private void cropPhoto(Uri contentUri) {
        verifyStoragePermissions(WaitPayActivity.this);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.setDataAndType(contentUri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        //intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
     */
    private void verifyStoragePermissions(WaitPayActivity waitPayActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (waitPayActivity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    waitPayActivity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    @Override
    public void onRefresh() {

        //获取订单详情
        getChatLsit();

    }

    private void addHeaderView() {
        if (kv.decodeString("id").equals(odrderIdvo.getC2cOrderDetailDTO().getUserId())) {
            //判断当前用户为买家
            if (odrderIdvo.getC2cOrderDetailDTO().getState() == 1) {
                main_header_search_qingkong.setVisibility(View.VISIBLE);
                header_title_name.setText("标记已付款");
                btn_Type1.setText("标记已付款");
                btn_Type1.setClickable(true);
                TxSee.setVisibility(View.VISIBLE);
                btn_Type1.setBackgroundResource(R.drawable.backbtn);
                btn_Type1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view1 = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                        TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                        Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                        Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                        mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
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
                                //确认付款
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
                            //天
                            day = millisUntilFinished / (1000 * 60 * 60 * 24);
                            //时
                            hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                            //分
                            minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                            //秒
                            second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
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
                header_title_name.setText("标记已收款");
                btn_Type1.setText("标记已收款");
                TxSee.setVisibility(View.GONE);
                btn_Type1.setClickable(true);
                btn_Type1.setBackgroundResource(R.drawable.backbtn);
                btn_Type1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view1 = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                        TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                        Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                        Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                        mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
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
                                //确认收款
                                isShpuMoney();
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
//        adapter = new ChatAdapter(mContext, mList);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        adapter.setHeaderView(headerView);
//        c2cRecView.setAdapter(adapter);
    }

    /**
     * 确认收款
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
                        Toast.makeText(getActivity(), data1.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }

    /**
     * 确认付款
     */
    @SuppressLint("CheckResult")
    private void isMoney() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", odrderIdvo.getC2cOrderDetailDTO().getId());
        ApiFactory.getInstance()
                .updatePayBeConfirm(kv.decodeString("id"), requestParam)
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
                                    View view1 = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                                    TextView tltle = (TextView) view1.findViewById(R.id.tltle);
                                    Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                                    Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
                                    mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
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
                                            isShpuMoney();//确认收款
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

                        Toast.makeText(getActivity(), data1.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), data1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });

    }
}
