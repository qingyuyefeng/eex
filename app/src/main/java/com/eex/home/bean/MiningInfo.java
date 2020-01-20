package com.eex.home.bean;

import java.math.BigDecimal;
import java.util.List;

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
 * @ClassName: MiningInfo
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 12:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 12:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MiningInfo {

    private String coin;
    private int rate;
    private BigDecimal computing;
    private boolean ebtMining;
    private BigDecimal daymining;
    private List<String> dellist;
    private boolean open;
    private BigDecimal miningNum;
    private boolean inti;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public BigDecimal getComputing() {
        return computing;
    }

    public void setComputing(BigDecimal computing) {
        this.computing = computing;
    }

    public boolean isEbtMining() {
        return ebtMining;
    }

    public void setEbtMining(boolean ebtMining) {
        this.ebtMining = ebtMining;
    }

    public BigDecimal getDaymining() {
        return daymining;
    }

    public void setDaymining(BigDecimal daymining) {
        this.daymining = daymining;
    }

    public List<String> getDellist() {
        return dellist;
    }

    public void setDellist(List<String> dellist) {
        this.dellist = dellist;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public BigDecimal getMiningNum() {
        return miningNum;
    }

    public void setMiningNum(BigDecimal miningNum) {
        this.miningNum = miningNum;
    }

    public boolean isInti() {
        return inti;
    }

    public void setInti(boolean inti) {
        this.inti = inti;
    }
}
