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
 * 佛祖保佑 永无BUG   bug找不出来
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.bean
 * @ClassName: OrderDetailDTO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderDetailDTO {

    private String id;
    private String userId;
    private String orderNo;
    private BigDecimal unitPrice;
    private BigDecimal priceRatio;
    private BigDecimal price;
    private String serviceFee;
    private String coinCode;
    private BigDecimal dealNum;
    private Integer state;
    private String payType;
    private Integer transactionType;
    private String sellId;
    private String adUserId;
    private String sellOrderNo;
    private BigDecimal cnyeMaxMun;
    private BigDecimal cnyeMinMun;
    private long payEndTime;
    private long confirmTime;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPriceRatio() {
        return priceRatio;
    }

    public void setPriceRatio(BigDecimal priceRatio) {
        this.priceRatio = priceRatio;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getDealNum() {
        return dealNum;
    }

    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(String adUserId) {
        this.adUserId = adUserId;
    }

    public String getSellOrderNo() {
        return sellOrderNo;
    }

    public void setSellOrderNo(String sellOrderNo) {
        this.sellOrderNo = sellOrderNo;
    }

    public BigDecimal getCnyeMaxMun() {
        return cnyeMaxMun;
    }

    public void setCnyeMaxMun(BigDecimal cnyeMaxMun) {
        this.cnyeMaxMun = cnyeMaxMun;
    }

    public BigDecimal getCnyeMinMun() {
        return cnyeMinMun;
    }

    public void setCnyeMinMun(BigDecimal cnyeMinMun) {
        this.cnyeMinMun = cnyeMinMun;
    }

    public long getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(long payEndTime) {
        this.payEndTime = payEndTime;
    }

    public long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", unitPrice=" + unitPrice +
                ", priceRatio=" + priceRatio +
                ", price=" + price +
                ", serviceFee='" + serviceFee + '\'' +
                ", coinCode='" + coinCode + '\'' +
                ", dealNum=" + dealNum +
                ", state=" + state +
                ", payType='" + payType + '\'' +
                ", transactionType=" + transactionType +
                ", sellId='" + sellId + '\'' +
                ", adUserId='" + adUserId + '\'' +
                ", sellOrderNo='" + sellOrderNo + '\'' +
                ", cnyeMaxMun=" + cnyeMaxMun +
                ", cnyeMinMun=" + cnyeMinMun +
                ", payEndTime=" + payEndTime +
                ", confirmTime=" + confirmTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
