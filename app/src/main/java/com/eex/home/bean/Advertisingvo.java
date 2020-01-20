package com.eex.home.bean;

import java.math.BigDecimal;
import java.util.List;

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
 * @ClassName: Advertisingvo
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 15:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 15:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * c2c广告列表
 */
public class Advertisingvo {

    private List<Integer> accountType;
    private BigDecimal maxTradeNum;
    private BigDecimal minTradeNum;
    private BigDecimal premium;
    private BigDecimal tradePrice;
    private BigDecimal quantity;
    private Integer tradeCount;
    private Integer tradeOKCount;
    private String merchName;
    private Integer merchantStatus;
    private String tradeCoin;
    private String id;
    private BigDecimal tradeNum;


    public List<Integer> getAccountType() {
        return accountType;
    }

    public void setAccountType(List<Integer> accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getMaxTradeNum() {
        return maxTradeNum;
    }

    public void setMaxTradeNum(BigDecimal maxTradeNum) {
        this.maxTradeNum = maxTradeNum;
    }

    public BigDecimal getMinTradeNum() {
        return minTradeNum;
    }

    public void setMinTradeNum(BigDecimal minTradeNum) {
        this.minTradeNum = minTradeNum;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Integer getTradeOKCount() {
        return tradeOKCount;
    }

    public void setTradeOKCount(Integer tradeOKCount) {
        this.tradeOKCount = tradeOKCount;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public Integer getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(Integer merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }
}
