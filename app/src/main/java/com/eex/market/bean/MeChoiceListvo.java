package com.eex.market.bean;

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
 * @ClassName: MeChoiceListvo
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/20 16:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MeChoiceListvo {
    private String id;
    private String userId;
    private String dealpear;
    private int sort;
    private boolean cktype;

    private int other;

    /**
     * 1 是限制买入
     */
    private Integer buyState;
    /**
     * 1 是限制卖出
     */
    private Integer sellState;

    public boolean isCktype() {
        return cktype;
    }

    public void setCktype(boolean cktype) {
        this.cktype = cktype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealpear() {
        return dealpear;
    }

    public void setDealpear(String dealpear) {
        this.dealpear = dealpear;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public Integer getBuyState() {
        return buyState;
    }

    public void setBuyState(Integer buyState) {
        this.buyState = buyState;
    }

    public Integer getSellState() {
        return sellState;
    }

    public void setSellState(Integer sellState) {
        this.sellState = sellState;
    }

    @Override
    public String toString() {
        return "MeChoiceListvo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", dealpear='" + dealpear + '\'' +
                ", sort=" + sort +
                ", cktype=" + cktype +
                ", other=" + other +
                ", buyState=" + buyState +
                ", sellState=" + sellState +
                '}';
    }
}
