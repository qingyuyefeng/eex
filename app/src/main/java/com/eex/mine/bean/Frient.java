package com.eex.mine.bean;

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
 * @ClassName: Frient
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 11:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 11:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Frient {

    private Integer firstCount;
    private String userName;
    private String id;
    private int count;

    /**
     * 合格EBX持仓总人数
     */
    private Long effectiveCount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(Integer firstCount) {
        this.firstCount = firstCount;
    }

    public Long getEffectiveCount() {
        return effectiveCount;
    }

    public void setEffectiveCount(Long effectiveCount) {
        this.effectiveCount = effectiveCount;
    }
}
