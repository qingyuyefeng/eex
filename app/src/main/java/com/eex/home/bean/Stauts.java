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
 * @ClassName: Stauts
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 11:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 是否实名
 */
public class Stauts {

    private Integer authStatus;
    private String remark;
    private Integer level;
    private Integer cardType;
    private String surname;
    private String givename;
    private String cardNo;

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivename() {
        return givename;
    }

    public void setGivename(String givename) {
        this.givename = givename;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }
}
