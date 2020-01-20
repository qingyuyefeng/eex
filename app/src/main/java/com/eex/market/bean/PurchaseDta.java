package com.eex.market.bean;

import java.math.BigDecimal;
import java.util.List;

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
 * @Package: com.overthrow.market.bean
 * @ClassName: PurchaseDta
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PurchaseDta {

    private String delKey;
    private BigDecimal usdtCny;
    private List<CnySize> listOrder;
    private List<Buy> buy;
    private  List<Sell> sell;
    private BigDecimal openingPrice;
    private BigDecimal closingPrice;
    private BigDecimal topPrice;
    private BigDecimal floorPrice;
    private BigDecimal dealQuantity;
    private String dealPair;
    private  String ktype;
    private BigDecimal delAmount;
    private BigDecimal residueNum;
    private long ktime;

    public BigDecimal getDelAmount() {
        return delAmount;
    }

    public void setDelAmount(BigDecimal delAmount) {
        this.delAmount = delAmount;
    }

    public BigDecimal getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(BigDecimal residueNum) {
        this.residueNum = residueNum;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(BigDecimal topPrice) {
        this.topPrice = topPrice;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
    }

    public BigDecimal getDealQuantity() {
        return dealQuantity;
    }

    public void setDealQuantity(BigDecimal dealQuantity) {
        this.dealQuantity = dealQuantity;
    }

    public String getDealPair() {
        return dealPair;
    }

    public void setDealPair(String dealPair) {
        this.dealPair = dealPair;
    }

    public String getKtype() {
        return ktype;
    }

    public void setKtype(String ktype) {
        this.ktype = ktype;
    }

    public long getKtime() {
        return ktime;
    }

    public void setKtime(long ktime) {
        this.ktime = ktime;
    }

    public String getDelKey() {
        return delKey;
    }

    public void setDelKey(String delKey) {
        this.delKey = delKey;
    }

    public BigDecimal getUsdtCny() {
        return usdtCny;
    }

    public void setUsdtCny(BigDecimal usdtCny) {
        this.usdtCny = usdtCny;
    }

    public List<CnySize> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<CnySize> listOrder) {
        this.listOrder = listOrder;
    }

    public List<Buy> getBuy() {
        return buy;
    }

    public void setBuy(List<Buy> buy) {
        this.buy = buy;
    }

    public List<Sell> getSell() {
        return sell;
    }

    public void setSell(List<Sell> sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "PurchaseDta{" +
                "delKey='" + delKey + '\'' +
                ", usdtCny=" + usdtCny +
                ", listOrder=" + listOrder +
                ", buy=" + buy +
                ", sell=" + sell +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ", topPrice=" + topPrice +
                ", floorPrice=" + floorPrice +
                ", dealQuantity=" + dealQuantity +
                ", dealPair='" + dealPair + '\'' +
                ", ktype='" + ktype + '\'' +
                ", delAmount=" + delAmount +
                ", residueNum=" + residueNum +
                ", ktime=" + ktime +
                '}';
    }
}
