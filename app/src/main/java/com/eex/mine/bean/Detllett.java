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
 * @ClassName: Detllett
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 10:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 10:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Detllett {

    private String coinCode;
    private BigDecimal history;
    private BigDecimal current;
    private BigDecimal lc;

    public BigDecimal getLc() {
        return lc;
    }

    public void setLc(BigDecimal lc) {
        this.lc = lc;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public BigDecimal getHistory() {
        return history;
    }

    public void setHistory(BigDecimal history) {
        this.history = history;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }
}
