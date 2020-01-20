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
 * @ClassName: AdvertisingUser
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/30 16:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/30 16:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AdvertisingUser {

    private List<Integer> accountType;
    private String createTime;
    private String id;
    private BigDecimal margin;
    private String marginCoin;
    private BigDecimal maxTradeNum;
    private String merchName;
    private Integer merchantStatus;
    private BigDecimal minTradeNum;
    private BigDecimal premium;
    private BigDecimal quantity;
    private String remark;
    private BigDecimal serviceRate;
    private Integer shelfStatus;
    private  String tradeCoin;
    private BigDecimal tradeCount;
    private BigDecimal tradeNum;
    private BigDecimal tradeOKCount;
    private BigDecimal tradePrice;
    private Integer tradeType;
    private String userId;
    private BigDecimal adOKNum;

    public BigDecimal getAdOKNum() {
        return adOKNum;
    }

    public void setAdOKNum(BigDecimal adOKNum) {
        this.adOKNum = adOKNum;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public List<Integer> getAccountType() {
        return accountType;
    }

    public void setAccountType(List<Integer> accountType) {
        this.accountType = accountType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public String getMarginCoin() {
        return marginCoin;
    }

    public void setMarginCoin(String marginCoin) {
        this.marginCoin = marginCoin;
    }

    public BigDecimal getMaxTradeNum() {
        return maxTradeNum;
    }

    public void setMaxTradeNum(BigDecimal maxTradeNum) {
        this.maxTradeNum = maxTradeNum;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public BigDecimal getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(BigDecimal tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }

    public BigDecimal getTradeOKCount() {
        return tradeOKCount;
    }

    public void setTradeOKCount(BigDecimal tradeOKCount) {
        this.tradeOKCount = tradeOKCount;
    }


    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
