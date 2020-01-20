package com.eex.mvp.market.fragment;

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
 * @ProjectName: Futures
 * @Package: com.futures.market.weight
 * @ClassName: EventS
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/11/27 15:32
 * @UpdateUser: 更新者
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EventS<T> {

    /**
     * 交易对
     */

    private String delKey;


    public EventS(String delKey) {
        this.delKey = delKey;

    }

    public String getDelKey() {
        return delKey;
    }

    public void setDelKey(String delKey) {
        this.delKey = delKey;
    }

}
