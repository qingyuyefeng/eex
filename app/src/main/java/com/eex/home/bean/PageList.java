package com.eex.home.bean;

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
 * @Package: com.overthrow.home.bean
 * @ClassName: PageList
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 21:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 21:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 我的挖矿
 */
public class PageList<T> {

    private ArrayList<T> resultList;
    public ArrayList<T> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<T> resultList) {
        this.resultList = resultList;
    }
}
