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
 * @ClassName: CnySize
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CnySize {

    //成交价
    private BigDecimal dealAmount;
    //成交量
    private BigDecimal dealNum;
    //成交时间
    private long  dealDate;

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public BigDecimal getDealNum() {
        return dealNum;
    }

    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }

    public long getDealDate() {
        return dealDate;
    }

    public void setDealDate(long dealDate) {
        this.dealDate = dealDate;
    }

    @Override
    public String toString() {
        return "CnySize{" +
                "dealAmount=" + dealAmount +
                ", dealNum=" + dealNum +
                ", dealDate=" + dealDate +
                '}';
    }
}
