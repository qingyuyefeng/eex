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
 * @ClassName: Storehouse
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 22:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 22:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Storehouse {

    private String id;
    private String coinCode;
    private String coinCodeProfit;
    private BigDecimal coinMoney;
    private String state;
    private String userId;
    private String fixedDailyRate;
    private BigDecimal activeRate;
    private String activeEndTime;
    private String activeStartTime;
    private BigDecimal lockFixedFee;
    private BigDecimal lockRateFee;
    private String lockStartTime;
    private String lockEndTime;
    private String orderNo;
    private BigDecimal fee;
    private long endDay;
    private BigDecimal minLockNum;
    private BigDecimal maxLockNum;
    private int financialCycle;
    private int financialCycleMonth;
    private int financialCycleYear;
    private BigDecimal lockOverplusMoney;
    private int feeDeductionType;
    private long lockEndTimeStamp;
    private BigDecimal serviceFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCoinCodeProfit() {
        return coinCodeProfit;
    }

    public void setCoinCodeProfit(String coinCodeProfit) {
        this.coinCodeProfit = coinCodeProfit;
    }

    public BigDecimal getCoinMoney() {
        return coinMoney;
    }

    public void setCoinMoney(BigDecimal coinMoney) {
        this.coinMoney = coinMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFixedDailyRate() {
        return fixedDailyRate;
    }

    public void setFixedDailyRate(String fixedDailyRate) {
        this.fixedDailyRate = fixedDailyRate;
    }

    public BigDecimal getActiveRate() {
        return activeRate;
    }

    public void setActiveRate(BigDecimal activeRate) {
        this.activeRate = activeRate;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public BigDecimal getLockFixedFee() {
        return lockFixedFee;
    }

    public void setLockFixedFee(BigDecimal lockFixedFee) {
        this.lockFixedFee = lockFixedFee;
    }

    public BigDecimal getLockRateFee() {
        return lockRateFee;
    }

    public void setLockRateFee(BigDecimal lockRateFee) {
        this.lockRateFee = lockRateFee;
    }

    public String getLockStartTime() {
        return lockStartTime;
    }

    public void setLockStartTime(String lockStartTime) {
        this.lockStartTime = lockStartTime;
    }

    public String getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(String lockEndTime) {
        this.lockEndTime = lockEndTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public long getEndDay() {
        return endDay;
    }

    public void setEndDay(long endDay) {
        this.endDay = endDay;
    }

    public BigDecimal getMinLockNum() {
        return minLockNum;
    }

    public void setMinLockNum(BigDecimal minLockNum) {
        this.minLockNum = minLockNum;
    }

    public BigDecimal getMaxLockNum() {
        return maxLockNum;
    }

    public void setMaxLockNum(BigDecimal maxLockNum) {
        this.maxLockNum = maxLockNum;
    }

    public int getFinancialCycle() {
        return financialCycle;
    }

    public void setFinancialCycle(int financialCycle) {
        this.financialCycle = financialCycle;
    }

    public int getFinancialCycleMonth() {
        return financialCycleMonth;
    }

    public void setFinancialCycleMonth(int financialCycleMonth) {
        this.financialCycleMonth = financialCycleMonth;
    }

    public int getFinancialCycleYear() {
        return financialCycleYear;
    }

    public void setFinancialCycleYear(int financialCycleYear) {
        this.financialCycleYear = financialCycleYear;
    }

    public BigDecimal getLockOverplusMoney() {
        return lockOverplusMoney;
    }

    public void setLockOverplusMoney(BigDecimal lockOverplusMoney) {
        this.lockOverplusMoney = lockOverplusMoney;
    }

    public int getFeeDeductionType() {
        return feeDeductionType;
    }

    public void setFeeDeductionType(int feeDeductionType) {
        this.feeDeductionType = feeDeductionType;
    }

    public long getLockEndTimeStamp() {
        return lockEndTimeStamp;
    }

    public void setLockEndTimeStamp(long lockEndTimeStamp) {
        this.lockEndTimeStamp = lockEndTimeStamp;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }
}
