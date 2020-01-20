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
 * @ClassName: CoinfigList
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 17:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 17:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * c2c获取法币交易列表
 */
public class CoinfigList {
    private String tradeCoin;
    private int buyStatus;
    private BigDecimal buyMargin;
    private String buyMarginCoin;
    private int sellStatus;
    private BigDecimal sellMargin;
    private String sellMarginCoin;
    private BigDecimal serviceRate;
    private boolean istype;

    public boolean istype() {
        return istype;
    }

    public void setIstype(boolean istype) {
        this.istype = true;
    }

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public int getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(int buyStatus) {
        this.buyStatus = buyStatus;
    }

    public BigDecimal getBuyMargin() {
        return buyMargin;
    }

    public void setBuyMargin(BigDecimal buyMargin) {
        this.buyMargin = buyMargin;
    }

    public String getBuyMarginCoin() {
        return buyMarginCoin;
    }

    public void setBuyMarginCoin(String buyMarginCoin) {
        this.buyMarginCoin = buyMarginCoin;
    }

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }

    public BigDecimal getSellMargin() {
        return sellMargin;
    }

    public void setSellMargin(BigDecimal sellMargin) {
        this.sellMargin = sellMargin;
    }

    public String getSellMarginCoin() {
        return sellMarginCoin;
    }

    public void setSellMarginCoin(String sellMarginCoin) {
        this.sellMarginCoin = sellMarginCoin;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }

}
