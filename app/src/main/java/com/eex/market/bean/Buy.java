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
 * @ClassName: Buy
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Buy {

    private BigDecimal delAmount;
    private BigDecimal residueNum;

    public BigDecimal getDelAmount() {
        return delAmount;
    }

    public void setDelAmount(BigDecimal delAmount) {
        this.delAmount = delAmount;
    }

    public BigDecimal getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(BigDecimal residueNum) {
        this.residueNum = residueNum;
    }

    @Override
    public String toString() {
        return "Buy{" +
                "delAmount=" + delAmount +
                ", residueNum=" + residueNum +
                '}';
    }
}
