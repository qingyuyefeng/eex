package com.eex.home.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

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
 * @ClassName: ConducMoney
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 18:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 18:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 */
public class ConducMoney<T> {

    private String id;
    private String coinCode;
    private String coinCodeProfit;
    private BigDecimal maxNum;
    private BigDecimal minNum;
    private BigDecimal lockFixedFee;
    private BigDecimal lockRateFee;
    private BigDecimal minLockNum;
    private int returnFeeType;
    private int levers;
    private ArrayList<T> cycleConfigVOList;
    private CoinCodeIwmBean coinCodeIwm;
    private CoinCodeProfitIwmBean coinCodeProfitIwm;


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

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getMinNum() {
        return minNum;
    }

    public void setMinNum(BigDecimal minNum) {
        this.minNum = minNum;
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

    public BigDecimal getMinLockNum() {
        return minLockNum;
    }

    public void setMinLockNum(BigDecimal minLockNum) {
        this.minLockNum = minLockNum;
    }

    public int getReturnFeeType() {
        return returnFeeType;
    }

    public void setReturnFeeType(int returnFeeType) {
        this.returnFeeType = returnFeeType;
    }

    public int getLevers() {
        return levers;
    }

    public void setLevers(int levers) {
        this.levers = levers;
    }

    public ArrayList<T> getCycleConfigVOList() {
        return cycleConfigVOList;
    }

    public void setCycleConfigVOList(ArrayList<T> cycleConfigVOList) {
        this.cycleConfigVOList = cycleConfigVOList;
    }

    public CoinCodeIwmBean getCoinCodeIwm() {
        return coinCodeIwm;
    }

    public void setCoinCodeIwm(CoinCodeIwmBean coinCodeIwm) {
        this.coinCodeIwm = coinCodeIwm;
    }

    public CoinCodeProfitIwmBean getCoinCodeProfitIwm() {
        return coinCodeProfitIwm;
    }

    public void setCoinCodeProfitIwm(CoinCodeProfitIwmBean coinCodeProfitIwm) {
        this.coinCodeProfitIwm = coinCodeProfitIwm;
    }
}
