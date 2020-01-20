package com.eex.mvp.mine.security.tradenick

import android.os.Bundle
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.trading.Trading
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_set_tradenick.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:09
 */
class TradeNickActivity : MVPBaseActivity<Trading, TradeNickContract.View, TradeNickPresenter>(), TradeNickContract.View {

    private var nick = ""

    override val layoutId: Int
        get() = R.layout.re_activity_set_tradenick

    override fun setNickSuccess(trading: Trading) {
        showToast(trading.msg)
        CommonUtil.hideKeyboard(this)
        setResult(222)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_ok.setOnClickListener {
            nick = et_nick.text.toString().trim()
            if (nick.isEmpty()) {
                showToast(R.string.please_input_nick)
                return@setOnClickListener
            }
            presenter.setNick(nick)
        }

    }
}