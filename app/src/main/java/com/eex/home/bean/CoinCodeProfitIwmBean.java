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
 * @ClassName: CoinCodeProfitIwmBean
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/20 10:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/20 10:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CoinCodeProfitIwmBean {

    /**
     * delKey : ETH_USDT
     * newPrice : 5.0
     * dealNum : -9.0164
     * higePrice : 5.0
     * fooPrice : 1.0
     * lastPrice : 1.0
     * dealDate : 1568878815129
     * usdtCny : 14.4634
     */

    private String delKey;
    private BigDecimal newPrice;
    private BigDecimal dealNum;
    private BigDecimal higePrice;
    private BigDecimal fooPrice;
    private BigDecimal lastPrice;
    private BigDecimal usdtCny;

    public String getDelKey() {
        return delKey;
    }

    public void setDelKey(String delKey) {
        this.delKey = delKey;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public BigDecimal getDealNum() {
        return dealNum;
    }

    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }

    public BigDecimal getHigePrice() {
        return higePrice;
    }

    public void setHigePrice(BigDecimal higePrice) {
        this.higePrice = higePrice;
    }

    public BigDecimal getFooPrice() {
        return fooPrice;
    }

    public void setFooPrice(BigDecimal fooPrice) {
        this.fooPrice = fooPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getUsdtCny() {
        return usdtCny;
    }

    public void setUsdtCny(BigDecimal usdtCny) {
        this.usdtCny = usdtCny;
    }
}
