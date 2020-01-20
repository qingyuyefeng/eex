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
 * @ClassName: CoinTradeConfig
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 12:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 12:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CoinTradeConfig {

    private String tradeCoin;
    private Integer buyStatus;
    private BigDecimal buyMargin;
    private String buyMarginCoin;
    private Integer sellStatus;
    private BigDecimal sellMargin;
    private String sellMarginCoin;
    private BigDecimal premiumMax;
    private BigDecimal premiumMin;
    private BigDecimal serviceRate;

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public BigDecimal getBuyMargin() {
        return buyMargin;
    }

    public void setBuyMargin(BigDecimal buyMargin) {
        this.buyMargin = buyMargin;
    }

    public String getBuyMarginCoin() {
        return buyMarginCoin;
    }

    public void setBuyMarginCoin(String buyMarginCoin) {
        this.buyMarginCoin = buyMarginCoin;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public BigDecimal getSellMargin() {
        return sellMargin;
    }

    public void setSellMargin(BigDecimal sellMargin) {
        this.sellMargin = sellMargin;
    }

    public String getSellMarginCoin() {
        return sellMarginCoin;
    }

    public void setSellMarginCoin(String sellMarginCoin) {
        this.sellMarginCoin = sellMarginCoin;
    }

    public BigDecimal getPremiumMax() {
        return premiumMax;
    }

    public void setPremiumMax(BigDecimal premiumMax) {
        this.premiumMax = premiumMax;
    }

    public BigDecimal getPremiumMin() {
        return premiumMin;
    }

    public void setPremiumMin(BigDecimal premiumMin) {
        this.premiumMin = premiumMin;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }
}
