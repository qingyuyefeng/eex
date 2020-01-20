package com.eex.mine.bean;

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
 * @Package: com.overthrow.mine.bean
 * @ClassName: RechargeFY
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 10:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 10:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RechargeFY {

    private long ebxTotal;
    private long friendNum;
    private BigDecimal chargesPrices;


    public long getEbxTotal() {
        return ebxTotal;
    }

    public void setEbxTotal(long ebxTotal) {
        this.ebxTotal = ebxTotal;
    }

    public long getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(long friendNum) {
        this.friendNum = friendNum;
    }

    public BigDecimal getChargesPrices() {
        return chargesPrices;
    }

    public void setChargesPrices(BigDecimal chargesPrices) {
        this.chargesPrices = chargesPrices;
    }
}
