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
 * @ClassName: PurchaseDatalIst
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PurchaseDatalIst {

    private BigDecimal dealPrice;
    private BigDecimal dealNum;
    private long dealTime;

    public PurchaseDatalIst(){}

    public PurchaseDatalIst(BigDecimal dealPrice, BigDecimal dealNum, long dealTime) {
        this.dealPrice = dealPrice;
        this.dealNum = dealNum;
        this.dealTime = dealTime;
    }

    public long getDealTime() {
        return dealTime;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getDealNum() {
        return dealNum;
    }

    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }
}
