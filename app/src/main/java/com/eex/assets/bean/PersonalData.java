package com.eex.assets.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

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
 * @ClassName: PersonalData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PersonalData<T> {

    private BigDecimal totalMoneyCny;
    private BigDecimal totalMoneyUsdt;
    private BigDecimal totalMoneyBtc;
    private BigDecimal frozenMoneyCny;
    private BigDecimal useMoneyCny;
    private ArrayList<T> userCoinAccount;


    public BigDecimal getTotalMoneyCny() {
        return totalMoneyCny;
    }

    public void setTotalMoneyCny(BigDecimal totalMoneyCny) {
        this.totalMoneyCny = totalMoneyCny;
    }

    public BigDecimal getTotalMoneyUsdt() {
        return totalMoneyUsdt;
    }

    public void setTotalMoneyUsdt(BigDecimal totalMoneyUsdt) {
        this.totalMoneyUsdt = totalMoneyUsdt;
    }

    public BigDecimal getTotalMoneyBtc() {
        return totalMoneyBtc;
    }

    public void setTotalMoneyBtc(BigDecimal totalMoneyBtc) {
        this.totalMoneyBtc = totalMoneyBtc;
    }

    public BigDecimal getFrozenMoneyCny() {
        return frozenMoneyCny;
    }

    public void setFrozenMoneyCny(BigDecimal frozenMoneyCny) {
        this.frozenMoneyCny = frozenMoneyCny;
    }

    public BigDecimal getUseMoneyCny() {
        return useMoneyCny;
    }

    public void setUseMoneyCny(BigDecimal useMoneyCny) {
        this.useMoneyCny = useMoneyCny;
    }

    public ArrayList<T> getUserCoinAccount() {
        return userCoinAccount;
    }

    public void setUserCoinAccount(ArrayList<T> userCoinAccount) {
        this.userCoinAccount = userCoinAccount;
    }
}
