package com.eex.mvp.trading;

import com.google.gson.annotations.SerializedName;
import com.icechao.klinelib.entity.KLineEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
 * @ProjectName: Futures
 * @Package: com.futures.trading.bean
 * @ClassName: KDataList
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/7 15:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/7 15:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KDataList extends KLineEntity {

  public static final String TAG = "KDataList";
  /**
   * K线时间
   */
  @SerializedName("ktime")
  public long  ktime;

  /**
   * 开盘价
   */
  @SerializedName("openingPrice")
  private Float openingPrice;

  /**
   * 收盘价
   */
  @SerializedName("closingPrice")
  private Float closingPrice;
  /**
   * 最高价
   */
  @SerializedName("topPrice")
  private float topPrice;
  /**
   * 最低价
   */
  @SerializedName("floorPrice")
  private float floorPrice;

  /**
   * 成交量
   */
  @SerializedName("dealQuantity")
  private float dealQuantity;

  /**
   * 交易对
   */

  private String dealPair;

  /**
   * K线类型
   */
  private String ktype;


  @Override
  @SerializedName("ktime")
  public Long getkTime() {
    return new Long(ktime);
  }

  public void setkTime(long kTime) {
    this.ktime = kTime;
  }

  @Override
  public Float getOpeningPrice() {
    return openingPrice;
  }

  public void setOpeningPrice(Float openingPrice) {
    this.openingPrice = openingPrice;
  }

  @Override
  public Float getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(Float closingPrice) {
    this.closingPrice = closingPrice;
  }

  @Override
  public float getTopPrice() {
    return topPrice;
  }

  public void setTopPrice(float topPrice) {
    this.topPrice = topPrice;
  }

  @Override
  public float getFloorPrice() {
    return floorPrice;
  }

  public void setFloorPrice(float floorPrice) {
    this.floorPrice = floorPrice;
  }

  @Override
  public float getDealQuantity() {
    return dealQuantity;
  }

  public void setDealQuantity(float dealQuantity) {
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



  @Override public String toString() {
    return "KDataList{" +
        "ktime=" + ktime +
        ", openingPrice=" + openingPrice +
        ", closingPrice=" + closingPrice +
        ", topPrice=" + topPrice +
        ", floorPrice=" + floorPrice +
        ", dealQuantity=" + dealQuantity +
        ", dealPair='" + dealPair + '\'' +
        ", ktype='" + ktype + '\'' +
//        ", newPrice=" + newPrice +
//        ", lastPrice=" + lastPrice +
        '}';
  }
}
