package com.eex.common.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
 * @ProjectName: MiningMachine
 * @Package: xinweilai.com.miningmachine.common.base
 * @ClassName: RecyclerAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/3/21 15:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/21 15:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class RecyclerAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public RecyclerAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

}
