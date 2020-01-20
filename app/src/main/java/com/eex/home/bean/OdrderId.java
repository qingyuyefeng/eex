package com.eex.home.bean;

import java.util.List;

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
 * @ClassName: OdrderId
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OdrderId {

    private Integer userType;
    private String phone;
    private String userName;
    private List<payListVO> payList;
    private OrderDetailDTO c2cOrderDetailDTO;
    private Integer dealNum30Day;
    private long currentTime;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<payListVO> getPayList() {
        return payList;
    }

    public void setPayList(List<payListVO> payList) {
        this.payList = payList;
    }

    public OrderDetailDTO getC2cOrderDetailDTO() {
        return c2cOrderDetailDTO;
    }

    public void setC2cOrderDetailDTO(OrderDetailDTO c2cOrderDetailDTO) {
        this.c2cOrderDetailDTO = c2cOrderDetailDTO;
    }

    public Integer getDealNum30Day() {
        return dealNum30Day;
    }

    public void setDealNum30Day(Integer dealNum30Day) {
        this.dealNum30Day = dealNum30Day;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    private String remark;
    private String realName;
    private String nickName;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "OdrderId{" +
                "userType=" + userType +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", payList=" + payList +
                ", c2cOrderDetailDTO=" + c2cOrderDetailDTO +
                ", dealNum30Day=" + dealNum30Day +
                ", currentTime=" + currentTime +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
