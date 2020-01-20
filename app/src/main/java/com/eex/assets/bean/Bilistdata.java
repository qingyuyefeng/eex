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
 * @ClassName: Bilistdata
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 13:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 13:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Bilistdata {
    private String id;
    private String coinId;
    private String coinCode;
    private String imgUrl;
    private BigDecimal fixedRate;
    private BigDecimal currencyRate;
    private BigDecimal coinMax;
    private BigDecimal coinMin;
    private BigDecimal minNum;
    private BigDecimal maxNum;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getMinNum() {
        return minNum;
    }

    public void setMinNum(BigDecimal minNum) {
        this.minNum = minNum;
    }

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getCoinMin() {
        return coinMin;
    }

    public void setCoinMin(BigDecimal coinMin) {
        this.coinMin = coinMin;
    }

    public BigDecimal getCoinMax() {
        return coinMax;
    }

    public void setCoinMax(BigDecimal coinMax) {
        this.coinMax = coinMax;
    }

    public BigDecimal getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(BigDecimal fixedRate) {
        this.fixedRate = fixedRate;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(BigDecimal currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }


}
