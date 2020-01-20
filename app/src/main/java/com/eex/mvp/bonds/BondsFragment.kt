package com.eex.mvp.bonds

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.eex.R
import com.eex.domin.entity.bonds.BondDealType.CALL_OPTION
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import com.eex.mvp.UpdateParamView
import com.eex.mvp.refresh.ItemLayoutContainer
import com.eex.mvp.refresh.RefreshBaseFragment
import kotlinx.android.synthetic.main.fragment_attorney.empty
import kotlinx.android.synthetic.main.fragment_attorney.recycler_view
import kotlinx.android.synthetic.main.fragment_attorney.swipe_refresh
import kotlinx.android.synthetic.main.item_bond.btn_cancel
import kotlinx.android.synthetic.main.item_bond.price
import kotlinx.android.synthetic.main.item_bond.profit_loss_volume
import kotlinx.android.synthetic.main.item_bond.quantity
import kotlinx.android.synthetic.main.item_bond.rate
import kotlinx.android.synthetic.main.item_bond.time
import kotlinx.android.synthetic.main.item_bond.tv_deal_type_tag
import kotlinx.android.synthetic.main.item_bond.tv_pair_name

open class BondsFragment<P : BondsBaseFragmentPresenter> : RefreshBaseFragment<Bonds,
    BondItem,
    BondsContract.View<BondItem>,
    P>(), BondsContract.View<BondItem>, UpdateParamView {

  override fun onNewParams(param: Bundle?) {
    presenter.onNewParams(param)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    swipe_refresh.setOnRefreshListener {
      onRefresh()
    }
//    onRefresh()
  }

  override fun getRecyclerView(): RecyclerView = recycler_view

  override fun itemLayoutId(): Int = R.layout.item_bond

  override fun obtainItemCls(): Class<*> = BondItem::class.java

  override fun genLayoutContainer(containerView: View): ItemLayoutContainer<BondItem> =
    object : ItemLayoutContainer<BondItem>(containerView) {
      override fun bindItem(item: BondItem) {
        tv_pair_name.text = item.pair
        tv_deal_type_tag.text = item.bondDealType.desc
        if (item.bondDealType == CALL_OPTION) {
          tv_deal_type_tag.setBackgroundResource(R.drawable.corner_solid_small_green)
        } else {
          tv_deal_type_tag.setBackgroundResource(R.drawable.corner_solid_a50000)
        }
        price.text = "买入价：${item.buyPrice}"
        time.text = item.tradeTime
        profit_loss_volume.text = item.balance.toPlainString()
        rate.text = "${item.balanceRate}%"
        quantity.text = "数量：${item.quantity}个"
        btn_cancel.setOnClickListener {
          presenter.cancel(item.id, item.pair, item.delegationType, item.attorneyStatus)
        }
      }
    }

  override val layoutId: Int
    get() = R.layout.fragment_attorney

  override fun refreshList(items: List<BondItem>) {
    adapter update {
      replaceAllItems(items.toMutableList(), TYPE_ITEM)
    }
  }

  override fun showRefreshing() {
    if (!swipe_refresh.isRefreshing) {
      swipe_refresh.isRefreshing = true
    }
  }

  override fun hideRefreshing() {
    swipe_refresh.isRefreshing = false
  }

  override fun showEmpty() {
    recycler_view.visibility = View.GONE
    empty.visibility = View.VISIBLE
  }

  override fun hideEmpty() {
    recycler_view.visibility = View.VISIBLE
    empty.visibility = View.GONE
  }
}