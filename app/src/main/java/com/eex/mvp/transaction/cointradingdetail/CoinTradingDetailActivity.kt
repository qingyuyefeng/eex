package com.eex.mvp.transaction.cointradingdetail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.eex.R
import com.eex.R.color
import com.eex.R.mipmap
import com.eex.WPConfig.WSUrl
import com.eex.common.api.ApiFactory
import com.eex.common.api.RxSchedulers
import com.eex.common.base.Data
import com.eex.common.base.UserConstants
import com.eex.common.util.CommonUtil
import com.eex.common.websocket.RxWebSocket
import com.eex.common.websocket.WebSocketSubscriber
import com.eex.constant.Constants
import com.eex.extensions.asyncAssign
import com.eex.mvp.NoNetBaseActivity
import com.eex.mvp.trading.FuturesTradingFragment
import com.eex.mvp.trading.KDataList
import com.eex.mvp.trading.KLineMenuView.OnTabSelectListener
import com.eex.navigation.Navigator
import com.google.gson.Gson
import com.icechao.klinelib.adapter.KLineChartAdapter
import com.icechao.klinelib.base.BaseKLineChartView.OnSelectedChangedListener
import com.icechao.klinelib.formatter.DateFormatter
import com.icechao.klinelib.formatter.ValueFormatter
import com.icechao.klinelib.utils.DateUtil
import com.icechao.klinelib.utils.SlidListener
import com.icechao.klinelib.utils.Status.IndexStatus
import com.icechao.klinelib.utils.Status.IndexStatus.MACD
import com.icechao.klinelib.utils.Status.KlineStatus.K_LINE
import com.icechao.klinelib.utils.Status.KlineStatus.TIME_LINE
import com.icechao.klinelib.utils.Status.MainStatus
import com.icechao.klinelib.utils.Status.MainStatus.MA
import com.icechao.klinelib.utils.Status.MaxMinCalcModel.CALC_CLOSE_WITH_LAST
import com.icechao.klinelib.utils.Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.kline_operator.*
import kotlinx.android.synthetic.main.kline_operator_time.*
import kotlinx.android.synthetic.main.re_activity_coin_trading_detal.*
import kotlinx.android.synthetic.main.view_coin_trading_detail_title.*
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.Date
import java.util.HashMap
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * @Description:币币交易详情（带K线）
 * @Author: xq
 * @CreateDate: 2020/1/16 18:00
 */
class CoinTradingDetailActivity : NoNetBaseActivity() {
    private var fragments: MutableList<Fragment> = ArrayList()
    private var vibrator: Vibrator? = null
    private var adapter: KLineChartAdapter<KDataList>? = null
    private val kChartBeans = java.util.ArrayList<KDataList>()
    /**
     * 从第多少条开始
     */
    private val startNo = 0
    /**
     * 每页多少条数据
     */
    private val endNo = 50000

    private var Time: String = ""
    private var Name: String = ""

    private val tabTitles =
            arrayListOf("时分", "1分钟", "15分钟", "1小时")

    private val mmkv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE)

    private var pairId = ""
    private var usdtCny = 1.0
    private var lastPri = 0.0

    private var delegationOrderFragment:DelegationOrderFragment? = null

    @RequiresApi(VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.re_activity_coin_trading_detal)
        initView()
        net("1min".also { Time = it })
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        initKLineView()
    }

    @RequiresApi(VERSION_CODES.O)
    fun initKLineView() {

        val loadingView = TextView(this)
        loadingView.text = "正在加载..."
        adapter = KLineChartAdapter<KDataList>()

        //设置数据适配器
        kLineChartView.setAdapter(adapter) //设置开场动画
                .setAnimLoadData(false) //设置开场动画
                .setChartItemWidth(50f) //设置每个点的宽度
                .setCandleWidth(30f) //设置蜡烛宽度
                .setSelectedPointRadius(20f) //设置十字线交点小圆颜色
                .setSelectedPointColor(Color.RED) //添加日期格式化,可动态修改
                .setDateTimeFormatter(object : DateFormatter() {
                    override fun format(date: Date): String {
                        return DateUtil.HHMMFormat.format(date)
                    }
                }) //网格列
                .setGridColumns(5) //网格行
                .setGridRows(5)
                .setLogoAlpha(100) //左滑超出宽度
                .setOverScrollRange(
                        windowManager.defaultDisplay.width / 5.toFloat()
                )
                .setCandleSolid(false)
                .setOnSelectedChangedListener(
                        OnSelectedChangedListener { view, index, values ->
                            vibrator?.vibrate(
                                    VibrationEffect.createOneShot(10, 100)
                            )
                        }) //滑动边f界监听(可能重复调用)
                .setSlidListener(object : SlidListener {
                    override fun onSlidLeft() {}
                    override fun onSlidRight() {}
                }) //Y值精度格式化(可重复设置)
                .setValueFormatter(object : ValueFormatter() {
                    override fun format(value: Float): String {
                        return String.format("%.03f", value)
                    }
                }) //成交量格式化
                .setVolFormatter(object : ValueFormatter() {
                    override fun format(value: Float): String {
                        return String.format("%.03f", value)
                    }
                }) //.setBetterX(true)
                .setBetterSelectedX(true) //显示loading
                .showLoading()
//        kLineChartView.scaleXMax = 1f
//        kLineChartView.scaleXMin = 0.5f
    }

    fun initView() {
        var data = intent.getBundleExtra("extra")
        val dealPair = data.getString("pair", "")
        tv_pair.text = if (dealPair.contains("_")) {
            dealPair.replace("_", "/")
        } else {
            dealPair
        }
        Name = if (dealPair.contains("/")) {
            dealPair.replace("/", "_")
        } else {
            dealPair
        }
        usdtCny = data.getDouble("cny", 1.0)
        cb_if_focus.isChecked = false

        delegationOrderFragment = DelegationOrderFragment.newInstance(Name)
        fragments.add(delegationOrderFragment!!)
        fragments.add(NewestDealsFragment.newInstance(Name))
        switchFragment(fragments[0])

        iv_back.setOnClickListener {
            onBackPressed()
        }

        btn_buy.setOnClickListener {
            onBackPressed()
        }
        btn_sell.setOnClickListener {
            onBackPressed()
        }

        rg_checks.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(group.findViewById(checkedId))
            switchFragment(fragments[index])
        }

        cb_if_focus.setOnClickListener {
            cb_if_focus.isEnabled = false
            if (cb_if_focus.isChecked) {
                focus(1)
            } else {
                focus(2)
            }
            Handler().postDelayed({
                cb_if_focus.isEnabled = true
            }, 1500)
        }

        sv_scroll.smoothScrollTo(0, 0)

        /**
         * 时分秒 分钟  小时 天 周 月
         * 默认 为1分钟
         * defaultIndex ==0；时分
         * defaultIndex ==1；1分钟
         * defaultIndex ==1；5分钟
         * defaultIndex ==1；15分钟
         * defaultIndex ==1；30分钟
         * defaultIndex ==1；1小时
         * defaultIndex ==1；1天
         * defaultIndex ==1；1周
         * defaultIndex ==1；1月
         */
        /**
         * 时分秒 分钟  小时 天 周 月
         * 默认 为1分钟
         * defaultIndex ==0；时分
         * defaultIndex ==1；1分钟
         * defaultIndex ==1；5分钟
         * defaultIndex ==1；15分钟
         * defaultIndex ==1；30分钟
         * defaultIndex ==1；1小时
         * defaultIndex ==1；1天
         * defaultIndex ==1；1周
         * defaultIndex ==1；1月
         */
        trading_atthe.setData(tabTitles, 1)
        trading_atthe.setOnTabSelectListener(OnTabSelectListener { position ->

            //时分
            when (position) {
                0 -> {
                    kLineChartView.setMaxMinCalcModel(
                            CALC_CLOSE_WITH_LAST
                    )
                    kLineChartView.hideSelectData()
                    kLineChartView.setKlineState(TIME_LINE)
                    trading_index.visibility = View.GONE
                    kline_operater.visibility = View.GONE
                    //1分钟
                }
                1 -> {
                    Time = "1min"
                    kLineChartView.hideSelectData()
                    kLineChartView.setKlineState(K_LINE)
                    trading_index.visibility = View.GONE
                    kline_operater.visibility = View.GONE
                }
                3 -> {
                    Time = "15min"
                    kLineChartView.setMaxMinCalcModel(
                            CALC_NORMAL_WITH_LAST
                    )
                    kLineChartView.hideSelectData()
                    kLineChartView.setKlineState(K_LINE)
                    trading_index.visibility = View.GONE
                    kline_operater.visibility = View.GONE
                }
                5 -> {
                    Time = "60min"
                    kLineChartView.setMaxMinCalcModel(
                            CALC_NORMAL_WITH_LAST
                    )
                    kLineChartView.hideSelectData()
                    kLineChartView.setKlineState(K_LINE)
                    trading_index.visibility = View.GONE
                    kline_operater.visibility = View.GONE
                }
            }
            net(Time)
        })

        //更多
        trading_more.setOnClickListener {
            if(kline_time.visibility == View.VISIBLE){
                kline_time.visibility = View.GONE
            }else{
                kline_time.visibility = View.VISIBLE
            }
            kline_operater.visibility = View.GONE
            trading_more.setTextColor(CommonUtil.getColor(color.text_color))
            trading_index.setTextColor(CommonUtil.getColor(color.color_4d6599))
            more_trading_image.background = resources.getDrawable(mipmap.trading_more)
        }
        //指标
        trading_index.setOnClickListener {
            kline_time.visibility = View.GONE
            if(kline_operater.visibility == View.VISIBLE){
                kline_operater.visibility = View.GONE
            }else{
                kline_operater.visibility = View.VISIBLE
            }
            linear_layout_master_operater.visibility = View.VISIBLE
            linear_layout_attached_operater.visibility = View.VISIBLE
            trading_index.setTextColor(CommonUtil.getColor(color.text_color))
            trading_more.setTextColor(CommonUtil.getColor(color.color_4d6599))
            more_index_image.background = resources.getDrawable(mipmap.trading_more)
        }

        //ma
        text_view_ma.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.changeMainDrawType(MA)
            text_view_ma.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_boll.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_macd.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_wr.setTextColor(CommonUtil.getColor(color.color_4d6599))
        }
        //boll
        text_view_boll.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.changeMainDrawType(MainStatus.BOLL)
            text_view_ma.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_boll.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_macd.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_wr.setTextColor(CommonUtil.getColor(color.color_4d6599))

        }
        //macd
        text_view_macd.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.setIndexDraw(MACD)
            text_view_ma.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_boll.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_macd.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_wr.setTextColor(CommonUtil.getColor(color.color_4d6599))
        }
        //kdj
        text_view_kdj.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.setIndexDraw(IndexStatus.KDJ)
            text_view_ma.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_boll.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_macd.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_wr.setTextColor(CommonUtil.getColor(color.color_4d6599))
        }
        //rsi
        text_view_rsi.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.setIndexDraw(IndexStatus.RSI)
            text_view_ma.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_boll.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_macd.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_wr.setTextColor(CommonUtil.getColor(color.color_4d6599))
        }

        //wr
        text_view_wr.setOnClickListener {
            kLineChartView.hideSelectData()
            kLineChartView.setIndexDraw(IndexStatus.WR)
            text_view_ma.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_boll.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_macd.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_kdj.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_rsi.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_wr.setTextColor(CommonUtil.getColor(color.text_color))
        }

        //5分钟
        text_view_one_minute.setOnClickListener {
            net("5min".also { Time = it })
            kLineChartView.hideSelectData()
            kLineChartView.setKlineState(K_LINE)
            text_view_one_minute.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_five_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_half_hour.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_week.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_mounth.setTextColor(CommonUtil.getColor(color.color_4d6599))

        }
        //一天
        text_view_five_minute.setOnClickListener {
            net("5min".also { Time = it })
            kLineChartView.hideSelectData()
            kLineChartView.setKlineState(K_LINE)
            text_view_one_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_five_minute.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_half_hour.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_week.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_mounth.setTextColor(CommonUtil.getColor(color.color_4d6599))

        }
        //一周
        text_view_half_hour.setOnClickListener {
            net("5min".also { Time = it })
            kLineChartView.hideSelectData()
            kLineChartView.setKlineState(K_LINE)
            text_view_one_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_five_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_half_hour.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_one_week.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_mounth.setTextColor(CommonUtil.getColor(color.color_4d6599))

        }
        //一月
        text_view_one_week.setOnClickListener {
            net("5min".also { Time = it })
            kLineChartView.hideSelectData()
            kLineChartView.setKlineState(K_LINE)
            text_view_one_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_five_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_half_hour.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_week.setTextColor(CommonUtil.getColor(color.text_color))
            text_view_one_mounth.setTextColor(CommonUtil.getColor(color.color_4d6599))

        }
        //一年
        text_view_one_mounth.setOnClickListener {
            net("5min".also { Time = it })
            kLineChartView.hideSelectData()
            kLineChartView.setKlineState(K_LINE)
            text_view_one_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_five_minute.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_half_hour.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_week.setTextColor(CommonUtil.getColor(color.color_4d6599))
            text_view_one_mounth.setTextColor(CommonUtil.getColor(color.text_color))

        }

    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        for (fragment1 in fragments) {
            if (fragment1.isAdded) {
                fragmentManager.beginTransaction()
                        .hide(fragment1)
                        .commit()
            }
        }
        if (fragment.isAdded) {
            fragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.rl_container, fragment)
                    .commit()
        }
    }

    @SuppressLint("CheckResult")
    private fun net(time: String) {
        clearSocket()
        val hashMap = HashMap<String, String>()
        //交易对
        hashMap["dealPair"] = Name
        //K线类型
        hashMap["ktype"] = time
        //开始条数
        hashMap["startNo"] = startNo.toString()
        //结束条数
        hashMap["endNo"] = endNo.toString()

        ApiFactory.getInstance()
                .getKDataListmacket(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(
                        { data: Data<ArrayList<KDataList>?> ->
                            if (data.data != null) {
                                //初始化控件加载数据
                                kChartBeans.clear()
                                kChartBeans.addAll(data.data!!)
                                initData(kChartBeans)
                                kLineChartView.hideSelectData()
                            } else {
                            }
                        }
                ) { throwable: Throwable? -> Timber.e(throwable) }
    }

    /**
     * 赋值K线
     */
    private fun initData(kDataList: ArrayList<KDataList>) {
        kDataList.sortWith(Comparator { o1, o2 ->
            val date1 = Date(o1.getkTime())
            val date2 = Date(o2.getkTime())
            // 对日期字段进行升序，如果欲降序可采用after方法
            if (date1.after(date2)) {
                1
            } else -1
        })
        adapter?.resetData(kDataList)
        kLineChartView.setIndexDraw(MACD)
        kLineChartView.hideLoading()
        Handler()
                .postDelayed({ initSocket() }
                        , 3000)
    }

    /**
     * ws：RxWebSocket
     *
     *
     * RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/" + Name + "_INDEX_PRICE")
     */
    private fun initSocket() {
        clearSocket()
        //ws://www.eex.la/futurewebsocket/BTC_USDT_PRICE_ANDROID
        socketSubscriber = genKlineSocket()
        socketSubscriber?.let {
            RxWebSocket.get(WSUrl + "websocket/" + Name)
                    .compose(RxSchedulers.io_main())
                    .subscribe(it)
        }
    }

    private fun clearSocket() {
        if (socketSubscriber != null && socketSubscriber!!.isDisposed) {
            socketSubscriber?.dispose()
        }
    }

    var socketSubscriber: WebSocketSubscriber? = null
    private var lastTime = 0L

    private fun genKlineSocket(): WebSocketSubscriber? {
        return object : WebSocketSubscriber() {
            @SuppressLint("SetTextI18n")
            override fun onMessage(data: String) {
                Log.e("xq","币币K线ws==>$data")
                if (TextUtils.isEmpty(data)) {
                    return
                }
                try {
                    val obj = JSONObject(data)
                    val kline = obj.optString("datatype", "")
                    if (!TextUtils.isEmpty(kline) && kline == "kline") {
                        try {
                            val kdata = Gson().fromJson(obj.optString("data", ""), KDataList::class.java)
                            if (kdata.closingPrice != null) {
                                val kk = kChartBeans[kChartBeans.size - 1]
                                if (kdata.closingPrice >= kk.closingPrice) {
                                    kdata.floorPrice = kk.closingPrice
                                } else {
                                    kdata.topPrice = kk.closingPrice
                                }
                                kdata.dealQuantity = kk.dealQuantity
                                kdata.setkTime(kdata.getkTime())
                                val curTime = System.currentTimeMillis()
                                if (FuturesTradingFragment.Time == "time") {
                                    adapter!!.addLast(kdata)
                                } else if (FuturesTradingFragment.Time == "1min") {
                                    if (curTime - lastTime >= 1000 * 60) {
                                        lastTime = curTime
                                        adapter!!.addLast(kdata)
                                    }
                                    adapter!!.changeItem(adapter!!.count - 1, kdata)
                                } else if (FuturesTradingFragment.Time == "5min") {
                                    if (curTime - lastTime >= 1000 * 60 * 5) {
                                        lastTime = curTime
                                        adapter!!.addLast(kdata)
                                    }
                                    adapter!!.changeItem(adapter!!.count - 1, kdata)
                                } else if (FuturesTradingFragment.Time == "15min") {
                                    if (curTime - lastTime >= 1000 * 60 * 15) {
                                        lastTime = curTime
                                        adapter!!.addLast(kdata)
                                    }
                                    adapter!!.changeItem(adapter!!.count - 1, kdata)
                                } else if (FuturesTradingFragment.Time == "30min") {
                                    if (curTime - lastTime >= 1000 * 60 * 30) {
                                        lastTime = curTime
                                        adapter!!.addLast(kdata)
                                    }
                                    adapter!!.changeItem(adapter!!.count - 1, kdata)
                                } else if (FuturesTradingFragment.Time == "60min") {
                                    if (curTime - lastTime >= 1000 * 60 * 60) {
                                        lastTime = curTime
                                        adapter!!.addLast(kdata)
                                    }
                                    adapter!!.changeItem(adapter!!.count - 1, kdata)
                                } else if (FuturesTradingFragment.Time == "1day") {
                                    adapter!!.addLast(kdata)
                                } else if (FuturesTradingFragment.Time == "1week") {
                                    adapter!!.addLast(kdata)
                                } else if (FuturesTradingFragment.Time == "1mon") {
                                    adapter!!.addLast(kdata)
                                }
                                kLineChartView.setMaxMinCalcModel(CALC_NORMAL_WITH_LAST)

                                tv_current_price.text = kdata.closingPrice.toString()
                                tv_cny_price.text = "≈${CommonUtil.cutNumber(kdata.closingPrice * usdtCny, 10)}CNY"
                                val newPrice = (kdata.closingPrice ?: 0f).toDouble()
                                val lastPrice = lastPri
                                tv_current_price.setTextColor(ContextCompat.getColor(this@CoinTradingDetailActivity,
                                        if (newPrice >= lastPrice) color.color_00a546 else color.color_cc3333))
                                tv_rate_change.text = if (lastPrice != 0.0)
                                    "${((((newPrice - lastPrice) / lastPrice) * 10000).toInt()) / 100.0}%"
                                else "0%"
                                tv_rate_change.setTextColor(ContextCompat.getColor(this@CoinTradingDetailActivity,
                                        if (newPrice >= lastPrice) color.color_00a546 else color.color_cc3333))
                                tv_rate_change.setBackgroundColor(ContextCompat.getColor(this@CoinTradingDetailActivity,
                                        if (newPrice >= lastPrice) R.color.color_3300a546 else R.color.color_33CC3333))

                                tv_24h.text = kdata.dealQuantity.toString()
                                tv_highest.text = kdata.topPrice.toString()
                                tv_lowest.text = kdata.floorPrice.toString()

                                lastPri = newPrice
                            }
                        } catch (e: Exception) {
                        }
                    } else if (kline == "tick") {
                        try {
                            val imdata = Gson().fromJson(obj.optString("data", ""), TickBean::class.java)
                            usdtCny = imdata.usdtCny ?: usdtCny
                            delegationOrderFragment?.getData(imdata)
                        } catch (e: Exception) {
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onReconnect() {}
        }
    }

    private fun focus(type: Int) {
        val token = mmkv.decodeString(Constants.TOKEN_ID, "")
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this, R.string.please_login_first, Toast.LENGTH_SHORT).show()
            Navigator.toLogin(this)
            return
        }
        when (type) {
            1 -> {
                val hashMap = HashMap<String, String>()
                hashMap["dealpear"] = Name
                ApiFactory.getInstance()
                        .saveOrUpdateDealPairCollection(token, hashMap)
                        .asyncAssign()
                        .subscribe { t ->
                            if (t.isStauts) {
                                if (t.data != null) {
                                    pairId = t.data.id
                                    cb_if_focus.isChecked = true
                                }
                            } else {
                                cb_if_focus.isChecked = false
                            }
                            Toast.makeText(this@CoinTradingDetailActivity, t.msg, Toast.LENGTH_SHORT).show()
                        }
            }
            2 -> {
                val hashMap = HashMap<String, String>()
                hashMap["ids"] = pairId
                ApiFactory.getInstance()
                        .cancelFocus(token, hashMap)
                        .asyncAssign()
                        .subscribe { t ->
                            cb_if_focus.isChecked = !t.isStauts
                            Toast.makeText(this@CoinTradingDetailActivity, t.msg, Toast.LENGTH_SHORT).show()
                        }
            }
        }
    }
}
