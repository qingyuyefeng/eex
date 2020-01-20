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
import com.eex.domin.entity.attorney.isIn
import com.eex.mvp.refresh.ItemLayoutContainer
import com.eex.mvp.refresh.RefreshBaseFragment
import kotlinx.android.synthetic.main.fragment_attorney.empty
import kotlinx.android.synthetic.main.fragment_attorney.recycler_view
import kotlinx.android.synthetic.main.fragment_attorney.swipe_refresh
import kotlinx.android.synthetic.main.item_attorney.tv_cancel
import kotlinx.android.synthetic.main.item_attorney.tv_deal_state
import kotlinx.android.synthetic.main.item_attorney.tv_deal_type
import kotlinx.android.synthetic.main.item_attorney.tv_price
import kotlinx.android.synthetic.main.item_attorney.tv_surplus_quantity
import kotlinx.android.synthetic.main.item_attorney.tv_time
import kotlinx.android.synthetic.main.item_attorney.tv_total_quantity
import kotlinx.android.synthetic.main.item_attorney.tv_unit_pair

open class AttorneyBaseFragment<P : AttorneyBasePresenter> : RefreshBaseFragment<
    Attorney,
    AttorneyItem,
    AttorneyContract.View<AttorneyItem>,
    P>(),AttorneyContract.View<AttorneyItem> {
  override fun getRecyclerView(): RecyclerView = recycler_view

  override val layoutId: Int
    get() = R.layout.fragment_attorney

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    swipe_refresh.setOnRefreshListener {
      onRefresh()
    }
  }

  override fun itemLayoutId(): Int = R.layout.item_attorney

  override fun obtainItemCls(): Class<*> = AttorneyItem::class.java

  override fun genLayoutContainer(containerView: View): ItemLayoutContainer<AttorneyItem> =
    object : ItemLayoutContainer<AttorneyItem>(containerView) {
      override fun bindItem(item: AttorneyItem) {
        tv_unit_pair.text = "${item.tradeUnit}/${item.fixedUnit}"
        if (item.dealType == BUY) {
          tv_deal_type.text = "买入"
        } else if (item.dealType == SALE) {
          tv_deal_type.text = "卖出"
        }
        tv_time.text = item.time
        tv_price.text = if (item.dealWay == 2) "市价" else "价格：${item.price} ${item.fixedUnit}"
        tv_total_quantity.text = "委托数：${item.totalQuantity}"
        tv_surplus_quantity.text =
          if (presenter.getDelegateType() == NORMAL) "剩余：${item.surplusQuantity}" else ""
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
        tv_cancel.visibility = if (item.status.isIn(
                PART_TRADE, DONE, PART_CANCEL
            )
        ) View.VISIBLE else View.GONE
        tv_cancel.setOnClickListener {
          presenter.cancel(item.id, item.tradeUnit, item.fixedUnit)
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