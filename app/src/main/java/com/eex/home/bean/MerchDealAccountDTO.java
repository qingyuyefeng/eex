package com.eex.home.bean;

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
 * @ClassName: MerchDealAccountDTO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/2 17:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/2 17:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MerchDealAccountDTO {


    /**
     * 主键
     */
    private String id;

    /**
     *商家ID(7ebit用户ID)
     */
    private String userId;
    /**
     *帐户类型(1银行，2支付宝，3微信)
     */
    private Integer accountType;
    /**
     *图片Url
     */
    private String imageUrl;
    /**
     *姓名
     */
    private String userName;
    /**
     *帐号
     */
    private String accountNo;
    /**
     *银行名称
     */
    private String bankName;
    /**
     *支行名称
     */
    private String childBankName;
    /**
     *银行卡所在地
     */
    private String bankAddress;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 真实姓名
     */
    private String realName;


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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChildBankName() {
        return childBankName;
    }

    public void setChildBankName(String childBankName) {
        this.childBankName = childBankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
