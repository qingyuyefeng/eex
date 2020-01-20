package com.eex.market.weight;

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
 * @Package: com.overthrow.market.weight
 * @ClassName: Root
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Root<T> {
    private String datatype;
    private T data;
    private int code;
    private String msg;
    private boolean stauts;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStauts() {
        return stauts;
    }

    public void setStauts(boolean stauts) {
        this.stauts = stauts;
    }

    @Override
    public String toString() {
        return "Root{" +
                "datatype='" + datatype + '\'' +
                ", data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", stauts=" + stauts +
                '}';
    }
}
