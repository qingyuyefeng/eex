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
 * @ClassName: CtcaccountList
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 14:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 14:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CtcaccountList {
    private BigDecimal btcabout;
    private BigDecimal cnyeabout;
    private BigDecimal frozenMoney;
    private BigDecimal totalAssets;
    private BigDecimal usableMoney;
    private String lockCoin;
    private String coinCode;
    private String imgUrl;
    private String userId;
    private String userName;

    public BigDecimal getBtcabout() {
        return btcabout;
    }

    public void setBtcabout(BigDecimal btcabout) {
        this.btcabout = btcabout;
    }

    public BigDecimal getCnyeabout() {
        return cnyeabout;
    }

    public void setCnyeabout(BigDecimal cnyeabout) {
        this.cnyeabout = cnyeabout;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getUsableMoney() {
        return usableMoney;
    }

    public void setUsableMoney(BigDecimal usableMoney) {
        this.usableMoney = usableMoney;
    }

    public String getLockCoin() {
        return lockCoin;
    }

    public void setLockCoin(String lockCoin) {
        this.lockCoin = lockCoin;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
