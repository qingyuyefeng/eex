package com.eex.home.bean;

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
 * @ClassName: payListVO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class payListVO {

    private String id;
    private String userId;
    private String imageUrl;
    private String userName;
    private String accountNo;
    private String bankName;
    private String childBankName;
    private String bankAddress;
    private Integer accountType;

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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "payListVO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", childBankName='" + childBankName + '\'' +
                ", bankAddress='" + bankAddress + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}
