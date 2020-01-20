package com.eex.home.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.eex.R
import com.eex.common.base.RecyclerAdapter
import com.eex.home.bean.CoinfigList

import java.util.ArrayList

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
 *
 *
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.adapter
 * @ClassName: GetCoinTradeConfigListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 14:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 14:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 *
 *
 * 交易币种
 */
class GetCoinTradeConfigListAdapter(list: ArrayList<CoinfigList>) : RecyclerAdapter<CoinfigList>(R.layout.item_coin_list, list) {

    override fun convert(helper: BaseViewHolder, item: CoinfigList) {

        helper.setText(R.id.coinName, item.tradeCoin)
        helper.addOnClickListener(R.id.coinName)
    }
}
