package com.eex.mine.bean;

import com.eex.home.bean.User;

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
 * @ClassName: GoogleBind
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 11:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 11:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GoogleBind extends User {


    private transient String googleKey;
    private transient int googleState;

    @Override
    public String getGoogleKey() {
        return googleKey;
    }

    @Override
    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

    @Override
    public int getGoogleState() {
        return googleState;
    }

    @Override
    public void setGoogleState(int googleState) {
        this.googleState = googleState;
    }
}
