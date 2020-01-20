package com.eex.mvp.mine.moneyaddress.addaddress

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.eex.R
import com.eex.common.view.SpinnerPopupWindow
import com.eex.domin.entity.moneyaddress.Address
import com.eex.domin.entity.moneyaddress.CoinBean
import com.eex.mvp.MVPBaseActivity
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.re_activity_add_address.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:26
 */
class AddAddressActivity : MVPBaseActivity<Address, AddAddrContract.View, AddAddrPresenter>(), AddAddrContract.View {

    private var spw: SpinnerPopupWindow<CoinBean>? = null

    private val ZXING_REQUEST = 123

    override val layoutId: Int
        get() = R.layout.re_activity_add_address


    override fun getCoinSuccess(address: Address) {
        if (address.coinList.isNotEmpty()) {
            tv_coin_type.text = address.coinList[0].coinCode
            presenter.coinList!!.addAll(address.coinList)
        }
    }

    override fun addSuccess(address: Address) {
        showToast(address.msg)
        setResult(111)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.getCoins()

        tv_coin_type.setOnClickListener {
            if (presenter.coinList!!.isEmpty()) {
                return@setOnClickListener
            }
            if (spw == null) {
                spw = SpinnerPopupWindow(this, presenter.coinList!!, tv_coin_type.measuredWidth)
                spw!!.setItemClick(object : SpinnerPopupWindow.ItemClick {
                    override fun itemClick(position: Int) {
                        tv_coin_type.text = presenter.coinList!![position].coinCode
                        spw!!.dismiss()
                    }
                })
            }
            spw?.showAsDropDown(tv_coin_type)
        }

        iv_sao.setOnClickListener {
            val intent = Intent(this, CaptureActivity::class.java)
            /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */

            /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */

            val config = ZxingConfig()
            //底部布局（包括闪光灯和相册）
            config.isShowbottomLayout = true
            //是否播放提示音
            config.isPlayBeep = true
            //是否震动
            config.isShake = true
            //是否显示相册
            config.isShowAlbum = true
            //是否显示闪光灯
            config.isShowFlashLight = true
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config)
            startActivityForResult(intent, ZXING_REQUEST)
        }

        btn_add_addr.setOnClickListener {
            val coinCode = tv_coin_type.text.toString()
            if (coinCode.isEmpty()) {
                showToast(R.string.please_choose_currency_type)
                return@setOnClickListener
            }
            val addr = et_wallet_address.text.toString().trim()
            if (addr.isEmpty()) {
                showToast(R.string.please_enter_wallet_address)
                return@setOnClickListener
            }
            val remark = et_remark.text.toString()
            presenter.addAddr(coinCode, addr, remark)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ZXING_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val content = data.getStringExtra(Constant.CODED_CONTENT)
                et_wallet_address.setText(content)
            }
        }
    }
}