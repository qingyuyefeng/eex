package com.eex.mvp.mine.userinfo.realname

import android.os.Bundle
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.common.view.SpinnerPopupWindow
import com.eex.domin.entity.realname.RealName
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_real_name.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 16:14
 */
class RealNameActivity : MVPBaseActivity<RealName, RealNameContract.View, RealNamePresenter>(), RealNameContract.View {

    private var cardType = 0 //0中国 else国外
    private var sex = "1" //1男 2女
    private var spw:SpinnerPopupWindow<String>? = null

    override val layoutId: Int
        get() = R.layout.re_activity_real_name

    override fun realNameSuccess(realName: RealName) {
        showToast(realName.msg)
        CommonUtil.hideKeyboard(this)
        setResult(111)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val areas = arrayListOf(getString(R.string.chinese_area),getString(R.string.other_country_area))
        tv_country_area.setOnClickListener {
            if (spw == null){
                spw = SpinnerPopupWindow(this,areas,tv_country_area.measuredWidth)
                spw!!.setItemClick(object :SpinnerPopupWindow.ItemClick{
                    override fun itemClick(position: Int) {
                        cardType = position
                        tv_country_area.text = areas[position]
                        if(position == 0){
                            tv_card_number.text = getString(R.string.id_card_number)
                        }else{
                            tv_card_number.text = getString(R.string.passport_number)
                        }
                        spw?.dismiss()
                    }
                })
            }
            spw?.showAsDropDown(tv_country_area)
        }


        rg_sex.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_sex1 -> sex = "1"
                R.id.rb_sex2 -> sex = "2"
            }
        }

        btn_sure.setOnClickListener {
            val surname = et_family_name.text.toString().trim()
            if (surname.isEmpty()) {
                showToast(R.string.pleasee_input_surname)
                return@setOnClickListener
            }

            val givenName = et_given_name.text.toString().trim()
            if (givenName.isEmpty()) {
                showToast(R.string.pleasee_input_givename)
                return@setOnClickListener
            }

            val idNumber = et_id_card_number.text.toString().trim()
            if (idNumber.isEmpty()) {
                if(cardType == 0){
                    showToast(R.string.pleasee_input_id_number)
                }else{
                    showToast(R.string.pleasee_input_passport_number)
                }
                return@setOnClickListener
            }
//            cardType = if (area.contains("中国")) "0" else "1"
            val area = if(cardType == 0) "中国" else "其他国家"
            presenter.realName(area, cardType.toString(), idNumber, sex, surname, givenName)
        }
    }
}