package com.eex.mvp.trading;

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
 * @ProjectName: EEX
 * @Package: com.eex.mvp.trading
 * @ClassName: IndexMarketList
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/30 14:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/30 14:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class IndexMarketList {

  /**
   * delKey : ETH_BTC
   * newPrice : 0.01831
   * dealNum : null
   * higePrice : 0.01832
   * fooPrice : 0.01811
   * lastPrice : 0.01815
   * dealDate : 1566883076739
   * cny : 7.1222
   * usdt : 186.833
   * usdtCny : 1330.66
   */

  private String delKey;
  private BigDecimal newPrice;
  private BigDecimal dealNum;
  private BigDecimal higePrice;
  private BigDecimal fooPrice;
  private BigDecimal lastPrice;
  private long dealDate;
  private BigDecimal cny;
  private BigDecimal usdt;
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

  public BigDecimal getCny() {
    return cny;
  }

  public void setCny(BigDecimal cny) {
    this.cny = cny;
  }

  public BigDecimal getUsdt() {
    return usdt;
  }

  public void setUsdt(BigDecimal usdt) {
    this.usdt = usdt;
  }

  public BigDecimal getUsdtCny() {
    return usdtCny;
  }

  public void setUsdtCny(BigDecimal usdtCny) {
    this.usdtCny = usdtCny;
  }

  public long getDealDate() {
    return new Long(dealDate);
  }

  public void setDealDate(long dealDate) {
    this.dealDate = dealDate;
  }

  @Override
  public String toString() {
    return "IndexMarketList{" +
        "delKey='" + delKey + '\'' +
        ", newPrice=" + newPrice +
        ", dealNum='" + dealNum + '\'' +
        ", higePrice=" + higePrice +
        ", fooPrice=" + fooPrice +
        ", lastPrice=" + lastPrice +
        ", cny=" + cny +
        ", usdt=" + usdt +
        ", usdtCny=" + usdtCny +
        '}';
  }
}
