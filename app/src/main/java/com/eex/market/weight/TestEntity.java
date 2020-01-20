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
 * @ProjectName: overthrow
 * @Package: com.overthrow.market.weight
 * @ClassName: TestEntity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/11/6 16:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/6 16:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestEntity implements IPieElement{

    private float mValue;
    private String mColor;
    private String mDescription;

    public TestEntity(float value, String color, String description){
        mValue=value;
        mColor=color;
        mDescription =description;
    }
    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public String getColor() {
        return mColor;
    }

    public String getDescription() {
        return mDescription;
    }
}
