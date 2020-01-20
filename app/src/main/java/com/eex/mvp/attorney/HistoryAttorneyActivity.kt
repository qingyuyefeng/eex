package com.eex.mvp.attorney

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.eex.R
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.AttorneyStatus.CANCEL
import com.eex.domin.entity.attorney.AttorneyStatus.DONE
import com.eex.domin.entity.attorney.AttorneyStatus.PART_CANCEL
import com.eex.domin.entity.attorney.AttorneyStatus.PART_TRADE
import com.eex.domin.entity.attorney.AttorneyStatus.UN_TRADE
import com.eex.domin.entity.attorney.AttorneyType.NORMAL
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.attorney.TradeTriggerStatus
import com.eex.domin.entity.attorney.TradeTriggerStatus.FAIL
import com.eex.domin.entity.attorney.TradeTriggerStatus.SUCCESS
import com.eex.domin.entity.attorney.TradeTriggerStatus.UN_TRIGGER
import com.eex.mvp.histroyattorney.HistoryAttorneyPresenter
import com.eex.mvp.refresh.ItemLayoutContainer
import com.eex.mvp.refresh.RefreshBaseActivity
import com.eex.popup.AttorneyFiltratePoupWindow
import kotlinx.android.synthetic.main.activity_history_attorney_list.empty
import kotlinx.android.synthetic.main.fragment_attorney.recycler_view
import kotlinx.android.synthetic.main.fragment_attorney.swipe_refresh
import kotlinx.android.synthetic.main.item_attorney_history.tv_deal_quantity
import kotlinx.android.synthetic.main.item_attorney_history.tv_deal_state
import kotlinx.android.synthetic.main.item_attorney_history.tv_deal_type
import kotlinx.android.synthetic.main.item_attorney_history.tv_price
import kotlinx.android.synthetic.main.item_attorney_history.tv_time
import kotlinx.android.synthetic.main.item_attorney_history.tv_total_quantity
import kotlinx.android.synthetic.main.item_attorney_history.tv_unit_pair
import kotlinx.android.synthetic.main.view_simple_toolbar.simple_toolbar
import kotlinx.android.synthetic.main.view_simple_toolbar.tv_title

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class HistoryAttorneyActivity : RefreshBaseActivity<Attorney,
    AttorneyItem,
    AttorneyContract.View<AttorneyItem>,
    HistoryAttorneyPresenter>(),AttorneyContract.View<AttorneyItem> {
  override fun getRecyclerView(): RecyclerView = recycler_view

  override val layoutId: Int
    get() = R.layout.activity_history_attorney_list

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupToolbar(simple_toolbar)
    setNavigationIcon(R.mipmap.back_triangle) {
      close()
    }
    setTitle(R.string.history_delegation)
    titleColor = R.color.color_4d6599
    setRightMenu(R.string.filtrate) {
      AttorneyFiltratePoupWindow(
          this, presenter.getDelegateType(), presenter.getDealType()
      ) { attorneyType, dealType ->
        presenter.setDealType(dealType)
        presenter.setDelegateType(attorneyType)
        presenter.loadInitial()
      }.showAsDropDown(tv_title)
    }
    setRightMenuColor(R.color.color_0080ff)
    swipe_refresh.setOnRefreshListener {
      onRefresh()
    }
  }

  override fun onStart() {
    super.onStart()
    presenter.setType("3,4,5")
    onRefresh()
  }

  override fun itemLayoutId(): Int = R.layout.item_attorney_history

  override fun obtainItemCls(): Class<*> = AttorneyItem::class.java

  override fun genLayoutContainer(containerView: View) =
    object : ItemLayoutContainer<AttorneyItem>(containerView) {
      override fun bindItem(item: AttorneyItem) {
        tv_unit_pair.text = "${item.tradeUnit}/${item.fixedUnit}"
        if (item.dealType == BUY) {
          tv_deal_type.text = "买入"
        } else if (item.dealType == SALE) {
          tv_deal_type.text = "卖出"
        }
        tv_time.text = item.time
        tv_price.text = if (item.dealWay == 2) "市价" else "${item.price} ${item.fixedUnit}"
        tv_total_quantity.text = "委托：${item.totalQuantity}"
        tv_deal_quantity.text =
          if (presenter.getDelegateType() == NORMAL) "成交：${item.dealQuantity}" else ""
        tv_deal_state.text = if (presenter.getDelegateType() == NORMAL) when (item.status) {
          UN_TRADE -> getString(R.string.type1)
          PART_TRADE -> getString(R.string.type2)
          DONE -> getString(R.string.type3)
          PART_CANCEL -> getString(R.string.type4)
          CANCEL -> getString(R.string.type5)
        } else when (item.triggerStatus) {
          UN_TRIGGER -> getString(R.string.un_trigger)
          SUCCESS -> getString(R.string.trigger_success)
          FAIL -> getString(R.string.trigger_fail)
          TradeTriggerStatus.CANCEL -> getString(R.string.type5)
        }
      }
    }

  override fun refreshList(items: List<AttorneyItem>) {
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
