package com.eex.mvp.mine.bankcards

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.eex.R
import com.eex.domin.entity.bankcards.BankCardBean
import com.eex.domin.entity.bankcards.BankCards
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.mine.bankcards.adapter.BankCardAdapter
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_bankcards_manager.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 14:43
 */
class BankCardsManagerActivity : MVPBaseActivity<BankCards, BankCardsContract.View, BankCardsPresenter>(), BankCardsContract.View {

    private var adapter: BankCardAdapter? = null
    private var list: MutableList<BankCardBean>? = null

    private var flag = 0

    override val layoutId: Int = R.layout.re_bankcards_manager

    override fun getCardsSuccess(bankCards: BankCards) {
        if(bankCards.cardsList.isEmpty()){
            rv_bank_cards.visibility = View.GONE
            ll_add_bank_con.visibility = View.GONE
            ll_add_bank.visibility = View.VISIBLE
        }else{
            rv_bank_cards.visibility = View.VISIBLE
            ll_add_bank_con.visibility = View.VISIBLE
            ll_add_bank.visibility = View.GONE
            list?.clear()
            list!!.addAll(bankCards.cardsList)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun deleteSuccess(bankCards: BankCards) {
        showToast(bankCards.msg)
        list?.removeAt(bankCards.deleteId)
        adapter?.notifyItemRemoved(bankCards.deleteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flag = intent.getIntExtra("flag",0)
        initView()
        presenter.getData()
    }

    private fun initView() {
        list = ArrayList()
        adapter = BankCardAdapter(this@BankCardsManagerActivity, list!!)
        adapter?.setDeleteCard(object : BankCardAdapter.DeleteCard {
            override fun itemClick(bean: BankCardBean) {
                if(flag == 1){
                    val intent = Intent()
                    intent.putExtra("bean", bean)
                    setResult(250, intent)
                    finish()
                }
            }
            override fun delete(id: String, position: Int) {
                presenter.deleteItem(id, position)
            }
        })
        rv_bank_cards.adapter = adapter
        rv_bank_cards.layoutManager = LinearLayoutManager(this@BankCardsManagerActivity)

        ll_add_bank.setOnClickListener {
            Navigator.toAddBankCardsActivity(this@BankCardsManagerActivity,111)
        }
        ll_add_bank_con.setOnClickListener {
            Navigator.toAddBankCardsActivity(this@BankCardsManagerActivity,111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 111){
            presenter.getData()
        }
    }
}