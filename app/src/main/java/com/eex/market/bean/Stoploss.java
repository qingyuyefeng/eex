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
 * @ClassName: Stoploss
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 17:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 17:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Stoploss  {

    private String coinCode;
    private long createTime;
    private Integer dealType;
    private BigDecimal delAmount;
    private BigDecimal delNum;
    private String fixPriceCoinCode;
    private String orderNo;
    private BigDecimal ratio;
    private String remark;
    private BigDecimal serviceCharge;
    private BigDecimal triggerPrice;
    private Integer triggerState;
    private Integer triggerType;
    private String userName;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public Integer getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Integer triggerState) {
        this.triggerState = triggerState;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getDelAmount() {
        return delAmount;
    }

    public void setDelAmount(BigDecimal delAmount) {
        this.delAmount = delAmount;
    }

    public BigDecimal getDelNum() {
        return delNum;
    }

    public void setDelNum(BigDecimal delNum) {
        this.delNum = delNum;
    }

    public String getFixPriceCoinCode() {
        return fixPriceCoinCode;
    }

    public void setFixPriceCoinCode(String fixPriceCoinCode) {
        this.fixPriceCoinCode = fixPriceCoinCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(BigDecimal triggerPrice) {
        this.triggerPrice = triggerPrice;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
