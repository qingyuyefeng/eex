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
 * @ClassName: Personal
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Personal {

    private String coinCode;
    private String coinName;
    private String imgUrl;
    private BigDecimal coinMoney;
    private BigDecimal totalMoeny;
    private BigDecimal frozenMoney;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public BigDecimal getCoinMoney() {
        return coinMoney;
    }

    public void setCoinMoney(BigDecimal coinMoney) {
        this.coinMoney = coinMoney;
    }

    public BigDecimal getTotalMoeny() {
        return totalMoeny;
    }

    public void setTotalMoeny(BigDecimal totalMoeny) {
        this.totalMoeny = totalMoeny;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }
}
