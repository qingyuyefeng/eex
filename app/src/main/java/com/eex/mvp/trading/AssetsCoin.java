package com.eex.mvp.trading;

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
 * @ProjectName: EEX
 * @Package: com.eex.mvp.trading
 * @ClassName: AssetsCoin
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/30 15:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/30 15:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class AssetsCoin {

    /**
     * userId : 2d9f8ec3fca24423bb367ff28ad9e902
     * userName : 1142120950@qq.com
     * coinCode : USDT
     * usableMoney : 9987.37
     * frozenMoney : 12.63
     * money : null
     * assetsEnum : null
     * remark : null
     * version : 198
     */

    private String userId;
    private String userName;
    private String coinCode;
    private BigDecimal usableMoney;
    private BigDecimal frozenMoney;
    private Object money;
    private Object assetsEnum;
    private Object remark;
    private int version;

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

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getUsableMoney() {
        return usableMoney;
    }

    public void setUsableMoney(BigDecimal usableMoney) {
        this.usableMoney = usableMoney;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public Object getMoney() {
        return money;
    }

    public void setMoney(Object money) {
        this.money = money;
    }

    public Object getAssetsEnum() {
        return assetsEnum;
    }

    public void setAssetsEnum(Object assetsEnum) {
        this.assetsEnum = assetsEnum;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
