package com.eex.mvp.market.bean;

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
 * @ClassName: dealCoinDTOList
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 9:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 9:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class dealCoinDTOList {
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
     * dealCoinDTOList : null
     */

    private String id;
    private long createTime;
    private Object createUser;
    private long updateTime;
    private Object updateUser;
    private Object remark;
    private int version;
    private int deleteMark;
    private String pricingCoin;
    private String coinCode;
    private String introduce;
    private Object img;
    private double serviceCharge;
    private String lever;
    private int transaction;
    private String dealPair;
    private double minNum;
    private double maxNum;
    private int quantityRetention;
    private int priceReservation;
    private Object dealCoinDTOList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Object getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Object updateUser) {
        this.updateUser = updateUser;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(int deleteMark) {
        this.deleteMark = deleteMark;
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

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
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

    public Object getDealCoinDTOList() {
        return dealCoinDTOList;
    }

    public void setDealCoinDTOList(Object dealCoinDTOList) {
        this.dealCoinDTOList = dealCoinDTOList;
    }

    @Override public String toString() {
        return "dealCoinDTOList{" +
            "id='" + id + '\'' +
            ", createTime=" + createTime +
            ", createUser=" + createUser +
            ", updateTime=" + updateTime +
            ", updateUser=" + updateUser +
            ", remark=" + remark +
            ", version=" + version +
            ", deleteMark=" + deleteMark +
            ", pricingCoin='" + pricingCoin + '\'' +
            ", coinCode='" + coinCode + '\'' +
            ", introduce='" + introduce + '\'' +
            ", img=" + img +
            ", serviceCharge=" + serviceCharge +
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
