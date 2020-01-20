package com.eex.assets.bean;

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
 * @Package: com.overthrow.assets.bean
 * @ClassName: FunCode
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FunCode {

    private BigDecimal chargeFee;
    private BigDecimal chargeMaxNum;
    private BigDecimal chargeMinNum;
    private String currency;
    private String currencyStr;
    private BigDecimal withdrawFee;
    private BigDecimal withdrawMaxNum;
    private BigDecimal withdrawMinNum;

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public BigDecimal getChargeMaxNum() {
        return chargeMaxNum;
    }

    public void setChargeMaxNum(BigDecimal chargeMaxNum) {
        this.chargeMaxNum = chargeMaxNum;
    }

    public BigDecimal getChargeMinNum() {
        return chargeMinNum;
    }

    public void setChargeMinNum(BigDecimal chargeMinNum) {
        this.chargeMinNum = chargeMinNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyStr() {
        return currencyStr;
    }

    public void setCurrencyStr(String currencyStr) {
        this.currencyStr = currencyStr;
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getWithdrawMaxNum() {
        return withdrawMaxNum;
    }

    public void setWithdrawMaxNum(BigDecimal withdrawMaxNum) {
        this.withdrawMaxNum = withdrawMaxNum;
    }

    public BigDecimal getWithdrawMinNum() {
        return withdrawMinNum;
    }

    public void setWithdrawMinNum(BigDecimal withdrawMinNum) {
        this.withdrawMinNum = withdrawMinNum;
    }
}
