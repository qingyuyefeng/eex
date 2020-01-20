package com.eex.market.bean;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.market.bean
 * @ClassName: JsonRoot
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/11/5 12:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/5 12:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class JsonRoot<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }


    public void setList(List<T> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "JsonRoot{" +
                "list=" + list +
                '}';
    }
}


