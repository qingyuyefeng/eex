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
 * @ClassName: SecondlistData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 14:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 14:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 解除锁定
 */
public class SecondlistData {

    private String id;
    private String coinCode;
    private BigDecimal secKillValue;
    private BigDecimal secKillNum;
    private BigDecimal secKillSum;
    private BigDecimal newPrice;
    private BigDecimal priceSum;
    private int lockTime;
    private int state;
    private String unlockTime;
    private String orderNo;
    private String endDay;
    private long secKillEndTimeMil;
    private long currentTimeMillis;
    private String remark;
    private String createTime;

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

    public BigDecimal getSecKillValue() {
        return secKillValue;
    }

    public void setSecKillValue(BigDecimal secKillValue) {
        this.secKillValue = secKillValue;
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

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
