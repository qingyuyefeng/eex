package com.eex.mvp.market.bean;

import java.util.ArrayList;
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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.market.bean
 * @ClassName: DealPairList
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 9:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class DealPairList {


    /**
     * id : 1
     * createTime : 1561369565000
     * createUser : null
     * updateTime : 1575440965000
     * updateUser : null
     * remark : null
     * version : 1
     * deleteMark : 0
     * pricingCoin : USDT
     * coinCode : BTC
     * introduce : 2222
     * img : null
     * serviceCharge : 0.2
     * lever : 10_20_50_80_100
     * transaction : 1
     * dealPair : BTC_USDT
     * minNum : 0.01
     * maxNum : 1000.0
     * quantityRetention : 4
     * priceReservation : 3
     * dealCoinDTOList : [{"id":"1","createTime":1561369565000,"createUser":null,"updateTime":1575440965000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"BTC","introduce":"2222","img":null,"serviceCharge":0.2,"lever":"10_20_50_80_100","transaction":1,"dealPair":"BTC_USDT","minNum":0.01,"maxNum":1000,"quantityRetention":4,"priceReservation":3,"dealCoinDTOList":null},{"id":"2","createTime":1561448594000,"createUser":null,"updateTime":1575440959000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"ETH","introduce":"5555","img":null,"serviceCharge":0.2,"lever":"10_20_50_80_100","transaction":1,"dealPair":"ETH_USDT","minNum":0.01,"maxNum":10000,"quantityRetention":4,"priceReservation":3,"dealCoinDTOList":null},{"id":"4","createTime":1566268931000,"createUser":null,"updateTime":1575440931000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"EOS","introduce":"gjututju","img":"","serviceCharge":0.2,"lever":"10_20_50_80","transaction":1,"dealPair":"EOS_USDT","minNum":1,"maxNum":100000,"quantityRetention":4,"priceReservation":4,"dealCoinDTOList":null},{"id":"6","createTime":1575440546000,"createUser":null,"updateTime":1575440899000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"XRP","introduce":"1WQ","img":"","serviceCharge":0.2,"lever":"10_20_50","transaction":1,"dealPair":"XRP_USDT","minNum":10,"maxNum":100000,"quantityRetention":2,"priceReservation":5,"dealCoinDTOList":null},{"id":"8","createTime":1575441974000,"createUser":null,"updateTime":1575441974000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"BCH","introduce":"0000","img":"http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/2019-12-04/921707699bb54de8b2c0b2e3ee9d6ac6","serviceCharge":0.2,"lever":"10_20_50_80_100","transaction":1,"dealPair":"BCH_USDT","minNum":0.1,"maxNum":10000,"quantityRetention":4,"priceReservation":4,"dealCoinDTOList":null},{"id":"10","createTime":1576736380000,"createUser":null,"updateTime":1576736380000,"updateUser":null,"remark":null,"version":1,"deleteMark":0,"pricingCoin":"USDT","coinCode":"LTC","introduce":"000","img":"","serviceCharge":0.2,"lever":"10_20_50_80_100","transaction":1,"dealPair":"LTC_USDT","minNum":0.1,"maxNum":10000,"quantityRetention":4,"priceReservation":8,"dealCoinDTOList":null}]
     */

    private String id;

    private String pricingCoin;
    private String coinCode;
    private String introduce;
    private String lever;
    private int transaction;
    private String dealPair;
    private double minNum;
    private double maxNum;
    private int quantityRetention;
    private int priceReservation;
    private ArrayList<dealCoinDTOList> dealCoinDTOList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPricingCoin() {
        return pricingCoin;
    }

    public void setPricingCoin(String pricingCoin) {
        this.pricingCoin = pricingCoin;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLever() {
        return lever;
    }

    public void setLever(String lever) {
        this.lever = lever;
    }

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public String getDealPair() {
        return dealPair;
    }

    public void setDealPair(String dealPair) {
        this.dealPair = dealPair;
    }

    public double getMinNum() {
        return minNum;
    }

    public void setMinNum(double minNum) {
        this.minNum = minNum;
    }

    public double getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(double maxNum) {
        this.maxNum = maxNum;
    }

    public int getQuantityRetention() {
        return quantityRetention;
    }

    public void setQuantityRetention(int quantityRetention) {
        this.quantityRetention = quantityRetention;
    }

    public int getPriceReservation() {
        return priceReservation;
    }

    public void setPriceReservation(int priceReservation) {
        this.priceReservation = priceReservation;
    }

    public ArrayList<dealCoinDTOList> getDealCoinDTOList() {
        return dealCoinDTOList;
    }

    public void setDealCoinDTOList(
        ArrayList<dealCoinDTOList> dealCoinDTOList) {
        this.dealCoinDTOList = dealCoinDTOList;
    }

    @Override public String toString() {
        return "DealPairList{" +
            "id='" + id + '\'' +
            ", pricingCoin='" + pricingCoin + '\'' +
            ", coinCode='" + coinCode + '\'' +
            ", introduce='" + introduce + '\'' +
            ", lever='" + lever + '\'' +
            ", transaction=" + transaction +
            ", dealPair='" + dealPair + '\'' +
            ", minNum=" + minNum +
            ", maxNum=" + maxNum +
            ", quantityRetention=" + quantityRetention +
            ", priceReservation=" + priceReservation +
            ", dealCoinDTOList=" + dealCoinDTOList +
            '}';
    }
}
