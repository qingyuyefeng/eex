package com.eex.home.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
 * @ClassName: C2cOrderDetailDTO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/2 17:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/2 17:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2cOrderDetailDTO {
    /**
     * 主键ID
     */
    private String id;
    /**
     *买家id
     */
    private String userId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 单价CNY
     */
    private BigDecimal unitPrice;
    /**
     * 溢价比例
     */
    private BigDecimal priceRatio;
    /**
     * 总金额CNY
     */
    private BigDecimal price;

    /**
     * 手续费
     */

    private BigDecimal  serviceFee;
    /**
     *交易币种
     */
    private String coinCode;
    /**
     * 交易数量
     */
    private BigDecimal dealNum;
    /**
     * 订单状态 1:待付款、2待确认收款、3:已取消、4：已过期 5 已完成
     */

    private Integer state;
    /**
     * 交易方式 支付宝 微信 银行卡
     */
    private String payType;
    /**
     * 交易类型 1：卖出 2：买入
     */
    private Integer transactionType;
    /**
     * 卖/\）家 userId
     */
    private String sellId;


    /**
     * 广告发布人id
     */
    private String adUserId;

    /**
     * \商)家广告id
     */
    private String sellOrderNo;
    /**
     * 交易限额(CNY)最大
     */
    private BigDecimal cnyeMaxMun;
    /**
     * 交易限额(CNY)最小
     */
    private BigDecimal cnyeMinMun;
    /**
     * 截止付款时间
     */

    private long  payEndTime;
    /**
     * 标记付款时间
     */

    private long confirmTime;
    /**
     * 手动取消付款时间
     */
    private Timestamp cancelTime;
    /**
     * 自动取消（过期）时间
     */
    private Timestamp expireTime;
    /**
     * 订单完成（确认收款）时间
     */
    private Timestamp finisheTime;
    /**
     * 下单时间
     */
    private Timestamp createTime;

    private String userName;

    private String  sellName;

    /**
     * 买家手机号
     */
    private String userPhone;

    /**
     * 卖家手机号
     */
    private String sellPhone;



    /**
     * 卖家家成单量
     */
    private Long sellCount;

    /**
     * 买家是否为认证商家
     */
    private Integer userState;

    /**
     * 卖家是否为认证商家
     */
    private Integer sellState;

    /**
     * 交易说明
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPriceRatio() {
        return priceRatio;
    }

    public void setPriceRatio(BigDecimal priceRatio) {
        this.priceRatio = priceRatio;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getDealNum() {
        return dealNum;
    }

    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(String adUserId) {
        this.adUserId = adUserId;
    }

    public String getSellOrderNo() {
        return sellOrderNo;
    }

    public void setSellOrderNo(String sellOrderNo) {
        this.sellOrderNo = sellOrderNo;
    }

    public BigDecimal getCnyeMaxMun() {
        return cnyeMaxMun;
    }

    public void setCnyeMaxMun(BigDecimal cnyeMaxMun) {
        this.cnyeMaxMun = cnyeMaxMun;
    }

    public BigDecimal getCnyeMinMun() {
        return cnyeMinMun;
    }

    public void setCnyeMinMun(BigDecimal cnyeMinMun) {
        this.cnyeMinMun = cnyeMinMun;
    }

    public long getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(long payEndTime) {
        this.payEndTime = payEndTime;
    }

    public long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Timestamp getFinisheTime() {
        return finisheTime;
    }

    public void setFinisheTime(Timestamp finisheTime) {
        this.finisheTime = finisheTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSellPhone() {
        return sellPhone;
    }

    public void setSellPhone(String sellPhone) {
        this.sellPhone = sellPhone;
    }

    public Long getSellCount() {
        return sellCount;
    }

    public void setSellCount(Long sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getSellState() {
        return sellState;
    }

    public void setSellState(Integer sellState) {
        this.sellState = sellState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
