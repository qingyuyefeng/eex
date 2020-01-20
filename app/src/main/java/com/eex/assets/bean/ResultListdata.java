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
 * @ClassName: ResultListdata
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ResultListdata  {

    private String id;
    private String userId;
    private String dealOrderNo;
    private BigDecimal dealAmount;
    private String userBankCardNo;
    private String firstName;
    private String lastName;
    private Integer dealStatus;
    private long createTime;
    private BigDecimal serviceCharge;
    private String remark;
    private String currency;
    private String myAccountNo;

    public String getMyAccountNo() {
        return myAccountNo;
    }

    public void setMyAccountNo(String myAccountNo) {
        this.myAccountNo = myAccountNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

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

    public String getDealOrderNo() {
        return dealOrderNo;
    }

    public void setDealOrderNo(String dealOrderNo) {
        this.dealOrderNo = dealOrderNo;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getUserBankCardNo() {
        return userBankCardNo;
    }

    public void setUserBankCardNo(String userBankCardNo) {
        this.userBankCardNo = userBankCardNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
