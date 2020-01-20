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
 * @ClassName: User
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/4/29 16:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/29 16:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class User<T> {


    /**
     * stauts : true
     * code : 200
     * msg : 成功
     * data : {"id":"c939188e99714098bc3811170f68d136","openId":null,"tokenId":null,"username":"+86 13896318772","password":" ","accountPassWord":"a737c39f1bccc73e9203423b40363d9a","status":1,"email":null,"realName":1,"salt":" ","userCode":"10043292","invateUser":null,"hasEmail":0,"hasPhone":1,"lastLoginIp":null,"phone":"+86 13896318772","invateCode":"9a996b82041e4591b8dbf196814d2d21","logDTO":{"id":"96cc451d79824d048e99ac4e7f01bb61","userId":"c939188e99714098bc3811170f68d136","userName":"+86 13896318772","loginIp":"183.67.22.162","operation":"APP登录成功","createTime":1559542783836},"ctcDealState":null,"googleState":null,"examState":null,"googleKey":null,"createTime":null,"merchant":0}
     */


    /**
     * id : c939188e99714098bc3811170f68d136
     * openId : null
     * tokenId : null
     * username : +86 13896318772
     * password :
     * accountPassWord : a737c39f1bccc73e9203423b40363d9a
     * status : 1
     * email : null
     * realName : 1
     * salt :
     * userCode : 10043292
     * invateUser : null
     * hasEmail : 0
     * hasPhone : 1
     * lastLoginIp : null
     * phone : +86 13896318772
     * invateCode : 9a996b82041e4591b8dbf196814d2d21
     * logDTO : {"id":"96cc451d79824d048e99ac4e7f01bb61","userId":"c939188e99714098bc3811170f68d136","userName":"+86 13896318772","loginIp":"183.67.22.162","operation":"APP登录成功","createTime":1559542783836}
     * ctcDealState : null
     * googleState : null
     * examState : null
     * googleKey : null
     * createTime : null
     * merchant : 0
     */

    private String id;
    private String tokenId;
    private String username;
    private String password;
    private String accountPassWord;
    private int status;
    private String email;
    /**
     * 0未实名
     * 1实名 空没实名
     */
    private Integer realName;
    private String salt;
    private String userCode;
    private int hasEmail;
    private int hasPhone;

    private String phone;
    private String invateCode;
    private T logDTO;
    private int googleState;
    private Integer examState;
    private String googleKey;
    private int merchant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountPassWord() {
        return accountPassWord;
    }

    public void setAccountPassWord(String accountPassWord) {
        this.accountPassWord = accountPassWord;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRealName() {
        return realName;
    }

    public void setRealName(Integer realName) {
        this.realName = realName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getHasEmail() {
        return hasEmail;
    }

    public void setHasEmail(int hasEmail) {
        this.hasEmail = hasEmail;
    }

    public int getHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(int hasPhone) {
        this.hasPhone = hasPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvateCode() {
        return invateCode;
    }

    public void setInvateCode(String invateCode) {
        this.invateCode = invateCode;
    }

    public T getLogDTO() {
        return logDTO;
    }

    public void setLogDTO(T logDTO) {
        this.logDTO = logDTO;
    }

    public int getGoogleState() {
        return googleState;
    }

    public void setGoogleState(int googleState) {
        this.googleState = googleState;
    }

    public Integer getExamState() {
        return examState;
    }

    public void setExamState(Integer examState) {
        this.examState = examState;
    }

    public String getGoogleKey() {
        return googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

    public int getMerchant() {
        return merchant;
    }

    public void setMerchant(int merchant) {
        this.merchant = merchant;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountPassWord='" + accountPassWord + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", realName=" + realName +
                ", salt='" + salt + '\'' +
                ", userCode='" + userCode + '\'' +
                ", hasEmail=" + hasEmail +
                ", hasPhone=" + hasPhone +
                ", phone='" + phone + '\'' +
                ", invateCode='" + invateCode + '\'' +
                ", logDTO=" + logDTO +
                ", googleState=" + googleState +
                ", examState=" + examState +
                ", googleKey='" + googleKey + '\'' +
                ", merchant=" + merchant +
                '}';
    }
}
