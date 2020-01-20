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
 * @ClassName: LoginUser
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/24 16:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/24 16:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LoginUser {

    /**
     * stauts : true
     * code : 200
     * msg : 成功
     * data : {"id":"c939188e99714098bc3811170f68d136","openId":null,"tokenId":null,"username":"+86 13896318772","password":" ","accountPassWord":"a737c39f1bccc73e9203423b40363d9a","status":1,"email":null,"realName":1,"salt":" ","userCode":"10043292","invateUser":null,"hasEmail":0,"hasPhone":1,"lastLoginIp":null,"phone":"+86 13896318772","invateCode":"9a996b82041e4591b8dbf196814d2d21","logDTO":{"id":"96cc451d79824d048e99ac4e7f01bb61","userId":"c939188e99714098bc3811170f68d136","userName":"+86 13896318772","loginIp":"183.67.22.162","operation":"APP登录成功","createTime":1559542783836},"ctcDealState":null,"googleState":null,"examState":null,"googleKey":null,"createTime":null,"merchant":0}
     */


    /**
     * id : 96cc451d79824d048e99ac4e7f01bb61
     * userId : c939188e99714098bc3811170f68d136
     * userName : +86 13896318772
     * loginIp : 183.67.22.162
     * operation : APP登录成功
     * createTime : 1559542783836
     */

    private String id;
    private String userId;
    private String userName;
    private String phone;
    private String loginIp;
    private String operation;
    private long createTime;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "LoginUser{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", operation='" + operation + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
