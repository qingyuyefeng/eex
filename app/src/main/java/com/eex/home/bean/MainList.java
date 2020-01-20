package com.eex.home.bean;

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
 * @Package: com.overthrow.home.bean
 * @ClassName: MainList
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 11:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 11:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainList {



    /**
     * 交易币
     */
    private String coinCode;

    /**
     * 定价币
     */

    private String pricingCoin;

    /**
     * 卖方手续费率
     */

    private BigDecimal serviceCharge;

    /**
     * 买方手续费率
     */

    private BigDecimal buyCharge;

    /**
     * 交易对
     */

    private String dealPair;

    /**
     * 最小交易数量
     */

    private BigDecimal minNum;

    /**
     * 最大交易数量
     */
    private BigDecimal maxNum;

    /**
     * 数量保留位数
     */

    private BigDecimal quantityRetention;

    /**
     * 价格保留位数
     */
    private BigDecimal priceReservation;

    private int other;

    /**
     * 1 是限制买入
     */
    private int buyState;
    /**
     * 1 是限制卖出
     */
    private int sellState;



    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getPricingCoin() {
        return pricingCoin;
    }

    public void setPricingCoin(String pricingCoin) {
        this.pricingCoin = pricingCoin;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getBuyCharge() {
        return buyCharge;
    }

    public void setBuyCharge(BigDecimal buyCharge) {
        this.buyCharge = buyCharge;
    }

    public String getDealPair() {
        return dealPair;
    }

    public void setDealPair(String dealPair) {
        this.dealPair = dealPair;
    }

    public BigDecimal getMinNum() {
        return minNum;
    }

    public void setMinNum(BigDecimal minNum) {
        this.minNum = minNum;
    }

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getQuantityRetention() {
        return quantityRetention;
    }

    public void setQuantityRetention(BigDecimal quantityRetention) {
        this.quantityRetention = quantityRetention;
    }

    public BigDecimal getPriceReservation() {
        return priceReservation;
    }

    public void setPriceReservation(BigDecimal priceReservation) {
        this.priceReservation = priceReservation;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getBuyState() {
        return buyState;
    }

    public void setBuyState(int buyState) {
        this.buyState = buyState;
    }

    public int getSellState() {
        return sellState;
    }

    public void setSellState(int sellState) {
        this.sellState = sellState;
    }

    @Override
    public String toString() {
        return "MainList{" +
                "coinCode='" + coinCode + '\'' +
                ", pricingCoin='" + pricingCoin + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", buyCharge=" + buyCharge +
                ", dealPair='" + dealPair + '\'' +
                ", minNum=" + minNum +
                ", maxNum=" + maxNum +
                ", quantityRetention=" + quantityRetention +
                ", priceReservation=" + priceReservation +
                ", other=" + other +
                ", buyState=" + buyState +
                ", sellState=" + sellState +
                '}';
    }
}
