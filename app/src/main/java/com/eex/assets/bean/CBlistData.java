package com.eex.assets.bean;

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
 * @Package: com.overthrow.assets.bean
 * @ClassName: CBlistData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 10:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 10:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CBlistData {

    private String orderNo;
    private String userId;
    private String getCoinAddr;
    private  Integer dealType;
    private BigDecimal dealAmount;
    private String coinCode;
    private Integer confirNum;
    private String confirNumState;
    private String txId;
    private long createTime;
    private long updateTime;
    private String remark;
    private Integer dealStatus;

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGetCoinAddr() {
        return getCoinAddr;
    }

    public void setGetCoinAddr(String getCoinAddr) {
        this.getCoinAddr = getCoinAddr;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public Integer getConfirNum() {
        return confirNum;
    }

    public void setConfirNum(Integer confirNum) {
        this.confirNum = confirNum;
    }

    public String getConfirNumState() {
        return confirNumState;
    }

    public void setConfirNumState(String confirNumState) {
        this.confirNumState = confirNumState;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
