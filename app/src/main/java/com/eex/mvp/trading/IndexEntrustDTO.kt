package com.eex.mvp.trading

import java.math.BigDecimal
import java.sql.Timestamp

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
 *
 *
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: Futures
 * @Package: com.futures.trading.bean
 * @ClassName: IndexEntrustDTO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/16 17:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/16 17:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
data class IndexEntrustDTO(
        var type:Int = 1,
        var nowPrice:Double = 0.0,
        var actualServiceCharge: Any? = null,
        var aveAmount: Double = 0.0,
        var bondsNight: Int = 0,
        var coin: Int = 0,
        var coinCode: String = "",
        var createTime: Long = 0,
        var dealType: Int = 0,
        var delAmount: Double = 0.0,
        var delNum: Double = 0.0,
        var delStatus: Int = 0,
        var delWay: Int = 0,
        var delkey: String = "",
        var delsource: Int = 0,
        var earnestMoney: Double = 0.0,
        var id: String = "",
        var lever: Int = 0,
        var nightMoney: Double = 0.0,
        var opAvgPrice: Double = 0.0,
        var opCondition: Int = 0,
        var opPrice: Double = 0.0,
        var opResidueNum: Double = 0.0,
        var opStatus: Int = 0,
        var opTime: Any? = null,
        var opType: Any? = null,
        var priceReservation: Int = 0,
        var pricingCoin: String = "",
        var profitLoss: Double = 0.0,
        var quantityRetention: Double = 0.0,
        var ratio: Double = 0.0,
        var realName: Any? = null,
        var residueNum: Double = 0.0,
        var serviceCharge: Double = 0.0,
        var stopLoss: Double = 0.0,
        var stopLossPrice: Double = 0.0,
        var targetProfit: Double = 0.0,
        var targetProfitPrice: Double = 0.0,
        var updateTime: Long = 0,
        var userId: String = "",
        var userName: String = ""

)
