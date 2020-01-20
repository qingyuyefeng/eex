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
 * @ClassName: SecondkillDetails
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 16:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SecondkillDetails {
    private String coinCode;
    private BigDecimal marketValue;
    private BigDecimal secKillValue;
    private BigDecimal secKillDiscount;
    private BigDecimal secKillTotalNum;
    private BigDecimal secKillCurrentNum;
    private int lockTime;
    private String secKillStartTime;
    private String secKillEndTime;
    private long secKillEndTimeMil;
    private long currentTimeMillis;
    private String unlockTime;
    private int dealState;
    private String dealStateStr;
    private int state;
    private String stateStr;
    private String endDay;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getSecKillValue() {
        return secKillValue;
    }

    public void setSecKillValue(BigDecimal secKillValue) {
        this.secKillValue = secKillValue;
    }

    public BigDecimal getSecKillDiscount() {
        return secKillDiscount;
    }

    public void setSecKillDiscount(BigDecimal secKillDiscount) {
        this.secKillDiscount = secKillDiscount;
    }

    public BigDecimal getSecKillTotalNum() {
        return secKillTotalNum;
    }

    public void setSecKillTotalNum(BigDecimal secKillTotalNum) {
        this.secKillTotalNum = secKillTotalNum;
    }

    public BigDecimal getSecKillCurrentNum() {
        return secKillCurrentNum;
    }

    public void setSecKillCurrentNum(BigDecimal secKillCurrentNum) {
        this.secKillCurrentNum = secKillCurrentNum;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public String getSecKillStartTime() {
        return secKillStartTime;
    }

    public void setSecKillStartTime(String secKillStartTime) {
        this.secKillStartTime = secKillStartTime;
    }

    public String getSecKillEndTime() {
        return secKillEndTime;
    }

    public void setSecKillEndTime(String secKillEndTime) {
        this.secKillEndTime = secKillEndTime;
    }

    public long getSecKillEndTimeMil() {
        return secKillEndTimeMil;
    }

    public void setSecKillEndTimeMil(long secKillEndTimeMil) {
        this.secKillEndTimeMil = secKillEndTimeMil;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    public int getDealState() {
        return dealState;
    }

    public void setDealState(int dealState) {
        this.dealState = dealState;
    }

    public String getDealStateStr() {
        return dealStateStr;
    }

    public void setDealStateStr(String dealStateStr) {
        this.dealStateStr = dealStateStr;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
