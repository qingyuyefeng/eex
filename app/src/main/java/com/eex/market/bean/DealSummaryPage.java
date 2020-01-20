package com.eex.market.bean;

import java.math.BigDecimal;

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
 * @Package: com.overthrow.market.bean
 * @ClassName: DealSummaryPage
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/14 17:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/14 17:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DealSummaryPage {


    /**
     * 交易币
     */
    private String coinCode;
    /**
     * 定价币
     */
    private String fixPriceCoinCode;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 买入量
     */
    private BigDecimal buyDealNum;
    /**
     * 卖出量
     */
    private BigDecimal sellDealNum;
    /**
     * 买入手续费
     */
    private BigDecimal buyServiceCharge;
    /**
     * 卖出手续费
     */
    private BigDecimal sellServiceCharge;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getFixPriceCoinCode() {
        return fixPriceCoinCode;
    }

    public void setFixPriceCoinCode(String fixPriceCoinCode) {
        this.fixPriceCoinCode = fixPriceCoinCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getBuyDealNum() {
        return buyDealNum;
    }

    public void setBuyDealNum(BigDecimal buyDealNum) {
        this.buyDealNum = buyDealNum;
    }

    public BigDecimal getSellDealNum() {
        return sellDealNum;
    }

    public void setSellDealNum(BigDecimal sellDealNum) {
        this.sellDealNum = sellDealNum;
    }

    public BigDecimal getBuyServiceCharge() {
        return buyServiceCharge;
    }

    public void setBuyServiceCharge(BigDecimal buyServiceCharge) {
        this.buyServiceCharge = buyServiceCharge;
    }

    public BigDecimal getSellServiceCharge() {
        return sellServiceCharge;
    }

    public void setSellServiceCharge(BigDecimal sellServiceCharge) {
        this.sellServiceCharge = sellServiceCharge;
    }
}
