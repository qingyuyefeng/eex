package com.eex.mvp.trading;

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
 * @Package: com.futures.trading.bean
 * @ClassName: KlineIndexPrice
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/28 17:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/28 17:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KlineIndexPrice<T> {

    /**
     * datatype : klineIndexPrice
     * data : {"openingPrice":186.566,"closingPrice":186.626,"topPrice":186.64,"floorPrice":186.556,"dealQuantity":0,"dealPair":"ETH_USDT","ktype":"1min","ktime":1566882720000}
     */

    private String datatype;
    private T data;

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override public String toString() {
        return "KlineIndexPrice{" +
            "datatype='" + datatype + '\'' +
            ", data=" + data +
            '}';
    }
}
