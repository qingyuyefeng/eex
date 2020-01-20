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
 * @ClassName: SecondKillRecord
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 10:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 10:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 秒杀记录
 */
public class SecondKillRecord {

    private String coinCode;
    private String createTime;
    private long currentTimeMillis;
    private String endDay;
    private String id;
    private long lockTime;
    private BigDecimal newPrice;
    private String orderNo;
    private BigDecimal priceSum;
    private String remark;
    private long secKillEndTimeMil;
    private BigDecimal secKillNum;
    private BigDecimal secKillSum;
    private BigDecimal secKillValue;
    private int state;
    private String unlockTime;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSecKillEndTimeMil() {
        return secKillEndTimeMil;
    }

    public void setSecKillEndTimeMil(long secKillEndTimeMil) {
        this.secKillEndTimeMil = secKillEndTimeMil;
    }

    public BigDecimal getSecKillNum() {
        return secKillNum;
    }

    public void setSecKillNum(BigDecimal secKillNum) {
        this.secKillNum = secKillNum;
    }

    public BigDecimal getSecKillSum() {
        return secKillSum;
    }

    public void setSecKillSum(BigDecimal secKillSum) {
        this.secKillSum = secKillSum;
    }

    public BigDecimal getSecKillValue() {
        return secKillValue;
    }

    public void setSecKillValue(BigDecimal secKillValue) {
        this.secKillValue = secKillValue;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }
}
