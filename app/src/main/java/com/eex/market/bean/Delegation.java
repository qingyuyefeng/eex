package com.eex.market.bean;

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
 * @Package: com.overthrow.market.bean
 * @ClassName: Delegation
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 16:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 16:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Delegation {

    /**
     * 委托人ID
     */
    private String userId;

    /**
     * 委托人单号
     */
    private String orderNo;

    /**
     * 委托人帐号
     */
    private String userName;

    /**
     * 交易币
     */
    private String coinCode;

    private BigDecimal serviceCharge;


    /**
     * 定价币
     */
    private String fixPriceCoinCode;

    /**
     * 交易类型 (1 买  2 卖)
     */
    private Byte dealType;

    /**
     * 委托价格
     */
    private BigDecimal delAmount;

    /**
     * 委托数量
     */
    private BigDecimal delNum;

    /**
     * 剩余委托数量
     */
    private BigDecimal residueNum;

    /**
     * 委托状态(1未成交，2部分成交，3已完成，4部分成交已撤销 5已撤销)
     */
    private int delStatus;

    private Integer delWay;

    private BigDecimal aveAmount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 买入量
     */
    private BigDecimal buyDealNum;
    /**
     *卖出量
     */
    private BigDecimal sellDealNum;
    /**
     *买入手续费
     */
    private BigDecimal  buyServiceCharge;
    /**
     *卖出手续费
     */
    private BigDecimal sellServiceCharge;






    public Integer getDelWay() {
        return delWay;
    }

    public void setDelWay(Integer delWay) {
        this.delWay = delWay;
    }

    public BigDecimal getAveAmount() {
        return aveAmount;
    }

    public void setAveAmount(BigDecimal aveAmount) {
        this.aveAmount = aveAmount;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getFixPriceCoinCode() {
        return fixPriceCoinCode;
    }

    public void setFixPriceCoinCode(String fixPriceCoinCode) {
        this.fixPriceCoinCode = fixPriceCoinCode;
    }

    public Byte getDealType() {
        return dealType;
    }

    public void setDealType(Byte dealType) {
        this.dealType = dealType;
    }

    public BigDecimal getDelAmount() {
        return delAmount;
    }

    public void setDelAmount(BigDecimal delAmount) {
        this.delAmount = delAmount;
    }

    public BigDecimal getDelNum() {
        return delNum;
    }

    public void setDelNum(BigDecimal delNum) {
        this.delNum = delNum;
    }

    public BigDecimal getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(BigDecimal residueNum) {
        this.residueNum = residueNum;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getBuyDealNum() {
        return buyDealNum;
    }

    public void setBuyDealNum(BigDecimal buyDealNum) {
        this.buyDealNum = buyDealNum;
    }

    public BigDecimal getSellDealNum() {
        return sellDealNum;
    }

    public void setSellDealNum(BigDecimal sellDealNum) {
        this.sellDealNum = sellDealNum;
    }

    public BigDecimal getBuyServiceCharge() {
        return buyServiceCharge;
    }

    public void setBuyServiceCharge(BigDecimal buyServiceCharge) {
        this.buyServiceCharge = buyServiceCharge;
    }

    public BigDecimal getSellServiceCharge() {
        return sellServiceCharge;
    }

    public void setSellServiceCharge(BigDecimal sellServiceCharge) {
        this.sellServiceCharge = sellServiceCharge;
    }
}
