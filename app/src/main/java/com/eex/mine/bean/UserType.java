package com.eex.mine.bean;

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
 * @Package: com.overthrow.mine.bean
 * @ClassName: UserType
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/4 10:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/4 10:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UserType {


    private String id;
    private String userId;
    private Integer userGradeType;
    private String userName;
    private Integer userGrade;
    private String userGradeName;
    private String monthVol;
    private String ebtInventory;
    private long updateTime;
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

    public Integer getUserGradeType() {
        return userGradeType;
    }

    public void setUserGradeType(Integer userGradeType) {
        this.userGradeType = userGradeType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserGradeName() {
        return userGradeName;
    }

    public void setUserGradeName(String userGradeName) {
        this.userGradeName = userGradeName;
    }

    public String getMonthVol() {
        return monthVol;
    }

    public void setMonthVol(String monthVol) {
        this.monthVol = monthVol;
    }

    public String getEbtInventory() {
        return ebtInventory;
    }

    public void setEbtInventory(String ebtInventory) {
        this.ebtInventory = ebtInventory;
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
