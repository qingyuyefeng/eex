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
 * @ClassName: bIDataVO
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/25 13:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/25 13:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class bIDataVO {

    private String userId;
    private String coinCode;
    private BigDecimal coinMoney;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "bIDataVO{" +
                "userId='" + userId + '\'' +
                ", coinCode='" + coinCode + '\'' +
                ", coinMoney=" + coinMoney +
                '}';
    }
}
