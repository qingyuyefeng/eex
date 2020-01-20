package com.eex.assets.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

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
 * @ClassName: Ctcaccount
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 14:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 14:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Ctcaccount<T> {

    private BigDecimal btcTotalAssets;
    private BigDecimal cnyeTotalAssets;
    private ArrayList<T> list;

    public BigDecimal getBtcTotalAssets() {
        return btcTotalAssets;
    }

    public void setBtcTotalAssets(BigDecimal btcTotalAssets) {
        this.btcTotalAssets = btcTotalAssets;
    }

    public BigDecimal getCnyeTotalAssets() {
        return cnyeTotalAssets;
    }

    public void setCnyeTotalAssets(BigDecimal cnyeTotalAssets) {
        this.cnyeTotalAssets = cnyeTotalAssets;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
