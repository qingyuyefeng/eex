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
 * @ClassName: RechargeCZData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 15:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 15:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RechargeCZData {


    private String firstName;
    private String lastName;
    private String userBankCardNo;
    private BigDecimal dealAmount;
    private String dealOrderNo;
    private String bankName;
    private Integer dealStatus;
    private String childBankName;
    private String surname;
    private String givenName;
    private String remark;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChildBankName() {
        return childBankName;
    }

    public void setChildBankName(String childBankName) {
        this.childBankName = childBankName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getUserBankCardNo() {
        return userBankCardNo;
    }

    public void setUserBankCardNo(String userBankCardNo) {
        this.userBankCardNo = userBankCardNo;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getDealOrderNo() {
        return dealOrderNo;
    }

    public void setDealOrderNo(String dealOrderNo) {
        this.dealOrderNo = dealOrderNo;
    }
}
