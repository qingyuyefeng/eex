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
 * @ClassName: CentsRed
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 20:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 20:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 获取我的分紅列表
 */
public class CentsRed {


    private String coinCode;
    private BigDecimal coinWelfareNum;
    private BigDecimal coinWelfareLimit;
    private BigDecimal coinTotalWelfare;
    private BigDecimal lockWelfareNum;
    private BigDecimal coinCodeLimit;
    private BigDecimal lockTotalWelfare;
    private BigDecimal welfareTotal;
    private long createTime;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getCoinWelfareNum() {
        return coinWelfareNum;
    }

    public void setCoinWelfareNum(BigDecimal coinWelfareNum) {
        this.coinWelfareNum = coinWelfareNum;
    }

    public BigDecimal getCoinWelfareLimit() {
        return coinWelfareLimit;
    }

    public void setCoinWelfareLimit(BigDecimal coinWelfareLimit) {
        this.coinWelfareLimit = coinWelfareLimit;
    }

    public BigDecimal getCoinTotalWelfare() {
        return coinTotalWelfare;
    }

    public void setCoinTotalWelfare(BigDecimal coinTotalWelfare) {
        this.coinTotalWelfare = coinTotalWelfare;
    }

    public BigDecimal getLockWelfareNum() {
        return lockWelfareNum;
    }

    public void setLockWelfareNum(BigDecimal lockWelfareNum) {
        this.lockWelfareNum = lockWelfareNum;
    }

    public BigDecimal getCoinCodeLimit() {
        return coinCodeLimit;
    }

    public void setCoinCodeLimit(BigDecimal coinCodeLimit) {
        this.coinCodeLimit = coinCodeLimit;
    }

    public BigDecimal getLockTotalWelfare() {
        return lockTotalWelfare;
    }

    public void setLockTotalWelfare(BigDecimal lockTotalWelfare) {
        this.lockTotalWelfare = lockTotalWelfare;
    }

    public BigDecimal getWelfareTotal() {
        return welfareTotal;
    }

    public void setWelfareTotal(BigDecimal welfareTotal) {
        this.welfareTotal = welfareTotal;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
