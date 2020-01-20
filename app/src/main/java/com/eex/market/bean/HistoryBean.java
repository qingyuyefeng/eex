package com.eex.market.bean;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.market.bean
 * @ClassName: HistoryBean
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/11/4 11:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 11:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HistoryBean {

    /**
     * id : 0
     * coin_id :
     * buy_vol : 175.411880454
     * sell_vol : 174.29630458449998
     * buy_vol_usd : 1098.022
     * sell_vol_usd : 3170.9034
     * close : 0
     * volume_usd : 0
     * data :
     * timestamp : 1572837395
     */

    private int id;
    private String coin_id;
    private double buy_vol;
    private double sell_vol;
    private double buy_vol_usd;
    private double sell_vol_usd;
    private double close;
    private double volume_usd;
    private String data;
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoin_id() {
        return coin_id;
    }

    public void setCoin_id(String coin_id) {
        this.coin_id = coin_id;
    }

    public double getBuy_vol() {
        return buy_vol;
    }

    public void setBuy_vol(double buy_vol) {
        this.buy_vol = buy_vol;
    }

    public double getSell_vol() {
        return sell_vol;
    }

    public void setSell_vol(double sell_vol) {
        this.sell_vol = sell_vol;
    }

    public double getBuy_vol_usd() {
        return buy_vol_usd;
    }

    public void setBuy_vol_usd(double buy_vol_usd) {
        this.buy_vol_usd = buy_vol_usd;
    }

    public double getSell_vol_usd() {
        return sell_vol_usd;
    }

    public void setSell_vol_usd(double sell_vol_usd) {
        this.sell_vol_usd = sell_vol_usd;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume_usd() {
        return volume_usd;
    }

    public void setVolume_usd(double volume_usd) {
        this.volume_usd = volume_usd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id=" + id +
                ", coin_id='" + coin_id + '\'' +
                ", buy_vol=" + buy_vol +
                ", sell_vol=" + sell_vol +
                ", buy_vol_usd=" + buy_vol_usd +
                ", sell_vol_usd=" + sell_vol_usd +
                ", close=" + close +
                ", volume_usd=" + volume_usd +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
