package com.eex.market.bean;

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
 * @Package: com.overthrow.market.bean
 * @ClassName: Money
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/25 13:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/25 13:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Money {

    private BigDecimal usableCNY;
    private BigDecimal freezeCNY;
    private BigDecimal serviceMoney;
    private BigDecimal coinMoney;
    private String coinCode;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getCoinMoney() {
        return coinMoney;
    }

    public void setCoinMoney(BigDecimal coinMoney) {
        this.coinMoney = coinMoney;
    }

    public BigDecimal getUsableCNY() {
        return usableCNY;
    }

    public void setUsableCNY(BigDecimal usableCNY) {
        this.usableCNY = usableCNY;
    }

    public BigDecimal getFreezeCNY() {
        return freezeCNY;
    }

    public void setFreezeCNY(BigDecimal freezeCNY) {
        this.freezeCNY = freezeCNY;
    }

    public BigDecimal getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(BigDecimal serviceMoney) {
        this.serviceMoney = serviceMoney;
    }
}
