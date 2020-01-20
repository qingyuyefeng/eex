package com.eex.mvp.mine.userinfo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.eex.R
import com.eex.domin.entity.security.Security
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_userinfo.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/19 17:22
 */
class UserInfoActivity : MVPBaseActivity<Security, UserInfoContract.View, UserInfoPresenter>(), UserInfoContract.View {
    override val layoutId: Int
        get() = R.layout.re_activity_userinfo

    override fun getInfoSuccess(security: Security) {
        when (security.level) {
            1 -> {
                tv_if_level2.text = getString(R.string.not_approve)
                tv_if_level2.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
                tv_if_level3.text = getString(R.string.not_approve)
                tv_if_level3.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
            }
            2 -> {
                when (security.authStatus) { //0审核中  1审核通过  2审核拒绝
                    0 -> {
                        tv_if_level2.text = getString(R.string.checking)
                        tv_if_level2.setTextColor(ContextCompat.getColor(this, R.color.color_00a546))
                        ll_sgs_level2.isEnabled = false
                    }
                    1 -> {
                        tv_if_level2.text = getString(R.string.has_approve)
                        tv_if_level2.setTextColor(ContextCompat.getColor(this, R.color.color_00a546))
                        ll_sgs_level2.isEnabled = false
                    }
                    else -> {
                        tv_if_level2.text = getString(R.string.not_approve)
                        tv_if_level2.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
                        ll_sgs_level2.isEnabled = true
                    }
                }
            }
            3 -> {
                tv_if_level2.text = getString(R.string.has_approve)
                tv_if_level2.setTextColor(ContextCompat.getColor(this, R.color.color_00a546))
                ll_sgs_level2.isEnabled = false
                when (security.authStatus) { //0审核中  1审核通过  2审核拒绝
                    0 -> {
                        tv_if_level3.text = getString(R.string.checking)
                        tv_if_level3.setTextColor(ContextCompat.getColor(this, R.color.color_00a546))
                        ll_sgs_level3.isEnabled = false
                    }
                    1 -> {
                        tv_if_level3.text = getString(R.string.has_approve)
                        tv_if_level3.setTextColor(ContextCompat.getColor(this, R.color.color_00a546))
                        ll_sgs_level3.isEnabled = false
                    }
                    else -> {
                        tv_if_level3.text = getString(R.string.not_approve)
                        tv_if_level3.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
                        ll_sgs_level3.isEnabled = true
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.getInfo()

        ll_sgs_level2.setOnClickListener {
            Navigator.toRealNameActivity(this, 111)
        }
        ll_sgs_level3.setOnClickListener {
            Navigator.toHighSgsActivity(this, 222)
        }

    }
}