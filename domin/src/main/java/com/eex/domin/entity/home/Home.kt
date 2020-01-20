package com.eex.domin.entity.home

import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:19
 */
data class Home(
        val level: Int = -1,
        val bannerList: List<Banner> = ArrayList(),
        val list1: List<ListBean> = ArrayList(),
        val list2: List<HomeBean2> = ArrayList(),
        val noticeData: Notice = Notice(),
        val refreshBean: HomeBean2 = HomeBean2()
)

data class Banner(  //首页banner
        var id: String? = "",
        /**
         * banner 名称
         */
        var bannerName: String? = "",
        /**
         * 路径
         */
        var path: String? = "",
        /**
         * 外连地址
         */
        var outsideAddr: String? = ""

)

data class Notice(
        var 官方活动公告: List<Industry> = ArrayList()
)


data class Industry(
        var id: String = "",
        var title: String = "",
        var categoryName: String = "",
        var createTime: String = "",
        var content: String = "",
        var categoryId: String,
        var createUser: String,
        var hits: Int = 0,
        var outLinkMark: Int = 0,
        var outLinkUrl: Any? = null,
        var recomState: Int = 0,
        var remark: Any? = null,
        var saasId: Any? = null,
        var seoKeyword: Any? = null,
        var seoTitle: Any? = null,
        var shortContent: String = "",
        var sort: Int = 0,
        var source: Any? = null,
        var status: Any? = null,
        var titleImage: Any? = null,
        var topMark: Int = 0,
        var website: String = "",
        var websiteType: Int = 0
)


data class ListBean(
        /**
         * 交易币
         */
        var coinCode: String? = null,

        /**
         * 定价币
         */

        var pricingCoin: String? = null,

        /**
         * 卖方手续费率
         */

        var serviceCharge: Double? = null,

        /**
         * 买方手续费率
         */

        var buyCharge: Double? = null,

        /**
         * 交易对
         */

        var dealPair: String? = null,

        /**
         * 最小交易数量
         */

        var minNum: Double? = null,

        /**
         * 最大交易数量
         */
        var maxNum: Double? = null,

        /**
         * 数量保留位数
         */

        var quantityRetention: Double? = null,

        /**
         * 价格保留位数
         */
        var priceReservation: Double? = null,

        var other: Int = 0,

        /**
         * 1 是限制买入
         */
        var buyState: Int = 0,
        /**
         * 1 是限制卖出
         */
        var sellState: Int = 0

)


data class HomeBean2(
        /**
         * 交易对
         */
        var delKey: String? = "",

        /**
         * 最新价
         */
        var newPrice: Double? = 0.0,

        /**
         * 成交量
         */
        var dealNum: Double? = 0.0,

        /**
         * 最高价
         */
        var higePrice: Double? = 0.0,

        /**
         * 最低价
         */
        var fooPrice: Double? = 0.0,

        /**
         * 昨收价
         */
        var lastPrice: Double? = 0.0,

        /**
         * USDT_CNY价格
         */
        var usdtCny: BigDecimal? = BigDecimal(0)
)

