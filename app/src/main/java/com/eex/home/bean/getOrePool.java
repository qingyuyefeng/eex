package com.eex.home.bean;

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
 * @Package: com.overthrow.home.bean
 * @ClassName: getOrePool
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 9:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 9:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 挖矿详情
 */
public class getOrePool {

    private String coinCode;
    private String createTime;
    private String dealpair;
    private BigDecimal quantity;
    private BigDecimal serviceNum;
    private BigDecimal tradeNum;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDealpair() {
        return dealpair;
    }

    public void setDealpair(String dealpair) {
        this.dealpair = dealpair;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(BigDecimal serviceNum) {
        this.serviceNum = serviceNum;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }
}
