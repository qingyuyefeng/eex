package com.eex.mvp.assrtsjava.bean;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.bean
 * @ClassName: Assets
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 14:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 14:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class Assets {


    /**
     * userId : c939188e99714098bc3811170f68d136
     * userName : +86 13896318772
     * coinCode : USDT
     * usableMoney : 0
     * frozenMoney : 0
     * total : 0
     * btc : 0
     * usdt : 0
     * cnye : 0
     */

    private String userId;
    private String userName;
    private String coinCode;
    private BigDecimal usableMoney;
    private BigDecimal frozenMoney;
    private BigDecimal total;
    private BigDecimal btc;
    private BigDecimal usdt;
    private BigDecimal cnye;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBtc() {
        return btc;
    }

    public void setBtc(BigDecimal btc) {
        this.btc = btc;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }

    public void setUsdt(BigDecimal usdt) {
        this.usdt = usdt;
    }

    public BigDecimal getCnye() {
        return cnye;
    }

    public void setCnye(BigDecimal cnye) {
        this.cnye = cnye;
    }

}
