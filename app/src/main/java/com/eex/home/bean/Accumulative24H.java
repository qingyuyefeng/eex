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
 * @ClassName: Accumulative24H
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 21:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 21:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 24H待分红累计
 */
public class Accumulative24H {


    private String coinCode;
    private BigDecimal totalServiceCharge;
    private BigDecimal totalWelfare;
    private BigDecimal circuOneEbtWelfare;
    private BigDecimal lockOneEbtWelfare;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getTotalServiceCharge() {
        return totalServiceCharge;
    }

    public void setTotalServiceCharge(BigDecimal totalServiceCharge) {
        this.totalServiceCharge = totalServiceCharge;
    }

    public BigDecimal getTotalWelfare() {
        return totalWelfare;
    }

    public void setTotalWelfare(BigDecimal totalWelfare) {
        this.totalWelfare = totalWelfare;
    }

    public BigDecimal getCircuOneEbtWelfare() {
        return circuOneEbtWelfare;
    }

    public void setCircuOneEbtWelfare(BigDecimal circuOneEbtWelfare) {
        this.circuOneEbtWelfare = circuOneEbtWelfare;
    }

    public BigDecimal getLockOneEbtWelfare() {
        return lockOneEbtWelfare;
    }

    public void setLockOneEbtWelfare(BigDecimal lockOneEbtWelfare) {
        this.lockOneEbtWelfare = lockOneEbtWelfare;
    }

}
