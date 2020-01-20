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
 * @ClassName: PaningMoneyDetails
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PaningMoneyDetails {


    private long activeEndTime;
    private BigDecimal activeRate;
    private String activeStartTime;
    private String coinCode;
    private BigDecimal coinMoney;
    private long endDay;
    private BigDecimal fee;
    private int financialCycle;
    private int financialCycleMonth;
    private int financialCycleYear;
    private BigDecimal fixedDailyRate;
    private String id;
    private String lockEndTime;
    private BigDecimal lockFixedFee;
    private BigDecimal lockOverplusMoney;
    private BigDecimal lockRateFee;
    private String lockStartTime;
    private BigDecimal maxLockNum;
    private BigDecimal minLockNum;
    private String orderNo;
    private int state;
    private String userId;
    private int levers;
    private String coinCodeProfit;


    public long getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(long activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public BigDecimal getActiveRate() {
        return activeRate;
    }

    public void setActiveRate(BigDecimal activeRate) {
        this.activeRate = activeRate;
    }

    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getCoinMoney() {
        return coinMoney;
    }

    public void setCoinMoney(BigDecimal coinMoney) {
        this.coinMoney = coinMoney;
    }

    public long getEndDay() {
        return endDay;
    }

    public void setEndDay(long endDay) {
        this.endDay = endDay;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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

    public BigDecimal getFixedDailyRate() {
        return fixedDailyRate;
    }

    public void setFixedDailyRate(BigDecimal fixedDailyRate) {
        this.fixedDailyRate = fixedDailyRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(String lockEndTime) {
        this.lockEndTime = lockEndTime;
    }

    public BigDecimal getLockFixedFee() {
        return lockFixedFee;
    }

    public void setLockFixedFee(BigDecimal lockFixedFee) {
        this.lockFixedFee = lockFixedFee;
    }

    public BigDecimal getLockOverplusMoney() {
        return lockOverplusMoney;
    }

    public void setLockOverplusMoney(BigDecimal lockOverplusMoney) {
        this.lockOverplusMoney = lockOverplusMoney;
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

    public BigDecimal getMaxLockNum() {
        return maxLockNum;
    }

    public void setMaxLockNum(BigDecimal maxLockNum) {
        this.maxLockNum = maxLockNum;
    }

    public BigDecimal getMinLockNum() {
        return minLockNum;
    }

    public void setMinLockNum(BigDecimal minLockNum) {
        this.minLockNum = minLockNum;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLevers() {
        return levers;
    }

    public void setLevers(int levers) {
        this.levers = levers;
    }

    public String getCoinCodeProfit() {
        return coinCodeProfit;
    }

    public void setCoinCodeProfit(String coinCodeProfit) {
        this.coinCodeProfit = coinCodeProfit;
    }
}
