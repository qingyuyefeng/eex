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
 * @ClassName: MainData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 12:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 12:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainData {


    /**
     * 交易对
     */

    private String delKey;

    /**
     * 最新价
     */

    private BigDecimal newPrice;

    /**
     * 成交量
     */

    private BigDecimal dealNum;

    /**
     * 最高价
     */

    private BigDecimal higePrice;

    /**
     * 最低价
     */

    private BigDecimal fooPrice;

    /**
     * 昨收价
     */

    private BigDecimal lastPrice;

    /**
     * USDT_CNY价格
     */

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



    @Override
    public String toString() {
        return "MainData{" +
                "delKey='" + delKey + '\'' +
                ", newPrice=" + newPrice +
                ", dealNum=" + dealNum +
                ", higePrice=" + higePrice +
                ", fooPrice=" + fooPrice +
                ", lastPrice=" + lastPrice +
                ", usdtCny=" + usdtCny +
                '}';
    }
}
