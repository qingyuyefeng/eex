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
 * @ClassName: SecondkillLsitvo
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 10:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 10:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 秒杀专区
 */
public class SecondkillLsitvo {


    private String id;
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
    private int dealState;
    private String dealStateStr;
    private String imgUrl;
    private String coinName;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}
