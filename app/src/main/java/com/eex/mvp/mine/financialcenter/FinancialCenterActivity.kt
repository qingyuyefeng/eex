package com.eex.mvp.mine.financialcenter

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.eex.R
import com.eex.common.view.SpinnerPopupWindow
import com.eex.domin.entity.financialcenter.CycleConfigVO
import com.eex.domin.entity.financialcenter.FinancialCenter
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_financial_center.*
import java.lang.IndexOutOfBoundsException
import java.text.SimpleDateFormat

/**
 * @Description:理财中心
 * @Author: xq
 * @CreateDate: 2019/12/9 16:46
 */
class FinancialCenterActivity : MVPBaseActivity<FinancialCenter, FinancialCenterContract.View, FinancialCenterPresenter>(), FinancialCenterContract.View {

    private var sp: SpinnerPopupWindow<MoneyCoin>? = null

    private var alert: AlertDialog? = null

    override val layoutId: Int = R.layout.re_activity_financial_center

    override fun getCoinsSuccess(financialCenter: FinancialCenter) {
        if (financialCenter.coinList.isNotEmpty()) {
            setCoinText(financialCenter.coinList[0].coinCode)
            presenter.getCycleTypes(financialCenter.coinList[0].coinCode)
        }
    }

    override fun getCycleTypesSuccess(financialCenter: FinancialCenter) {

        if (financialCenter.coinCycleType.cycleConfigVOList.isEmpty()) {
            tv_financial_cycle.visibility = View.GONE
            rg_financial_cycle.visibility = View.GONE
            return
        }
        tv_financial_cycle.visibility = View.VISIBLE
        rg_financial_cycle.visibility = View.VISIBLE

        tv_can_use.text = "${((financialCenter.coinCycleType.maxNum * 100 + 0.005).toInt()) / 100.0}"
        try {
            for (i in financialCenter.coinCycleType.cycleConfigVOList.indices) {
                (rg_financial_cycle.getChildAt(i) as RadioButton).text = "${financialCenter.coinCycleType.cycleConfigVOList[i].financialCycle}" + getString(R.string.days)
            }
        } catch (e: IndexOutOfBoundsException) {
        }

        when (financialCenter.coinCycleType.cycleConfigVOList.size) {
            1 -> {
                rb_financial_cycle1.visibility = View.VISIBLE
                rb_financial_cycle2.visibility = View.INVISIBLE
                rb_financial_cycle3.visibility = View.INVISIBLE
                rb_financial_cycle4.visibility = View.INVISIBLE
                rb_financial_cycle5.visibility = View.GONE
            }
            2 -> {
                rb_financial_cycle1.visibility = View.VISIBLE
                rb_financial_cycle2.visibility = View.VISIBLE
                rb_financial_cycle3.visibility = View.INVISIBLE
                rb_financial_cycle4.visibility = View.INVISIBLE
                rb_financial_cycle5.visibility = View.GONE
            }
            3 -> {
                rb_financial_cycle1.visibility = View.VISIBLE
                rb_financial_cycle2.visibility = View.VISIBLE
                rb_financial_cycle3.visibility = View.VISIBLE
                rb_financial_cycle4.visibility = View.INVISIBLE
                rb_financial_cycle5.visibility = View.GONE
            }
            4 -> {
                rb_financial_cycle1.visibility = View.VISIBLE
                rb_financial_cycle2.visibility = View.VISIBLE
                rb_financial_cycle3.visibility = View.VISIBLE
                rb_financial_cycle4.visibility = View.VISIBLE
                rb_financial_cycle5.visibility = View.GONE
            }
            5 -> {
                rb_financial_cycle1.visibility = View.VISIBLE
                rb_financial_cycle2.visibility = View.VISIBLE
                rb_financial_cycle3.visibility = View.VISIBLE
                rb_financial_cycle4.visibility = View.VISIBLE
                rb_financial_cycle5.visibility = View.VISIBLE
            }
        }
        (rg_financial_cycle.getChildAt(0) as RadioButton).isChecked = true
        presenter.cycleType = financialCenter.coinCycleType.cycleConfigVOList[0].id

        setType(financialCenter, financialCenter.coinCycleType.cycleConfigVOList[0])

        rg_financial_cycle.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(group.findViewById(checkedId))

            val bean = financialCenter.coinCycleType.cycleConfigVOList[index]

            setType(financialCenter, bean)
        }

    }

    override fun getSettingsSuccess(financialCenter: FinancialCenter) {
        showToast(financialCenter.msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.getMoneyCoins()

        initView()
    }

    private fun initView() {

        title_bar.setRightIvShow()
        title_bar.setRightIvClick(View.OnClickListener {
            //跳转理财记录
            Navigator.toFinancialRecordsActivity(this)
        })

        tv_choose_product.setOnClickListener {
            if (presenter.proList!!.isEmpty()) {
                return@setOnClickListener
            }
            if (sp != null) {
                sp?.showAsDropDown(tv_choose_product)
            } else {
                sp = SpinnerPopupWindow(this@FinancialCenterActivity, presenter.proList!!, tv_choose_product.measuredWidth)
                sp!!.setItemClick(object : SpinnerPopupWindow.ItemClick {
                    override fun itemClick(position: Int) {
                        val coinCode: String = tv_choose_product.text.toString()
                        setCoinText(presenter.proList!![position].coinCode)
                        if (coinCode != presenter.proList!![position].coinCode) {
                            presenter.getCycleTypes(presenter.proList!![position].coinCode)
                        }
                        sp!!.dismiss()
                    }
                })
                sp?.showAsDropDown(tv_choose_product)
            }
        }

        rg_financial_type.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(group.findViewById(checkedId))
            presenter.checkType = index + 1
            hintAllTips(index)
        }
        tv_financial_status.setOnClickListener {
            val amount = et_input_amount.text.toString()
            if (amount.isEmpty()) {
                showToast(R.string.please_enter_recharge_amount)
                return@setOnClickListener
            }

            val amount1 = amount.toDouble()
            if (amount1 > presenter.voObservable.value!!.coinCycleType.maxNum || amount1 < presenter.voObservable.value!!.coinCycleType.minNum) {
                showToast(R.string.value_out_range)
            } else {
                showAlert(tv_choose_product.text.toString(), amount, presenter.cycleType, presenter.voObservable.value!!.coinCycleType.id, presenter.checkType.toString())
            }

        }
    }

    private fun showAlert(name: String, amount: String, cycleType: String, id: String, checkType: String) {
        if (alert == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.re_trading_pwd_dialog, null)
            val et_tradingPwd = view.findViewById<EditText>(R.id.et_trading_pwd)
            val bt_ok = view.findViewById<Button>(R.id.button)
            bt_ok.setOnClickListener {
                val pwd = et_tradingPwd.text.toString().trim()
                if (pwd.isEmpty()) {
                    showToast(R.string.please_enter_trading_pwd)
                    return@setOnClickListener
                }
                presenter.getSettings(pwd,
                        name,
                        amount,
                        cycleType,
                        id,
                        checkType)
                alert!!.dismiss()
            }
            alert = AlertDialog.Builder(this)
                    .setView(view)
                    .setCancelable(true)
                    .show()
        } else {
            alert?.show()
        }
    }

    private fun setType(financialCenter: FinancialCenter, bean: CycleConfigVO) {

        presenter.cycleType = bean.id
        tv_daily_rate.text = "${bean.fixedDailyRate * 100}%"
        tv_daily_earnings.text = "${((financialCenter.coinCycleType.maxNum * bean.fixedDailyRate * 100 + 0.005).toInt()) / 100.0}"

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = sdf.format(System.currentTimeMillis())
        if (currentTime < bean.activeStartTime) {
            tv_financial_status.text = getString(R.string.not_start)
            tv_financial_status.setBackgroundResource(R.drawable.btn_not_start_bg)
            tv_financial_status.isEnabled = false
        } else if (currentTime > bean.activeStartTime && currentTime < bean.activeEndTime) {
            tv_financial_status.text = getString(R.string.lock_up_financial)
            tv_financial_status.setBackgroundResource(R.drawable.btn_lock_repository_bg)
            tv_financial_status.isEnabled = true
        } else if (currentTime > bean.activeEndTime) {
            tv_financial_status.text = getString(R.string.have_finished)
            tv_financial_status.setBackgroundResource(R.drawable.btn_have_finished_bg)
            tv_financial_status.isEnabled = false
        }
    }

    private fun hintAllTips(index: Int) {
        tv_type_tip1.visibility = View.GONE
        tv_type_tip2.visibility = View.GONE
        tv_type_tip3.visibility = View.GONE
        when (index) {
            0 -> {
                tv_type_tip1.visibility = View.VISIBLE
            }
            1 -> {
                tv_type_tip2.visibility = View.VISIBLE
            }
            2 -> {
                tv_type_tip3.visibility = View.VISIBLE
            }
        }
    }

    private fun setCoinText(text: String) {
        tv_choose_product.text = text
        tv_coin_type1.text = text
        tv_coin_type2.text = text
        tv_coin_type3.text = text
    }
}
