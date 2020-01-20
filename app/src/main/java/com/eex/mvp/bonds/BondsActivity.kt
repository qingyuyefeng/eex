package com.eex.mvp.bonds

import android.os.Bundle
import android.support.v4.app.Fragment
import com.eex.R
import com.eex.R.string
import com.eex.common.view.ViewPagerFragmentAdapter
import com.eex.constant.Keys
import com.eex.domin.entity.bonds.Bonds
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.UpdateParamView
import com.eex.popup.BondsFiltratePoupWindow
import kotlinx.android.synthetic.main.activity_bonds.tab_layout
import kotlinx.android.synthetic.main.activity_bonds.view_pager
import kotlinx.android.synthetic.main.view_simple_toolbar.simple_toolbar
import kotlinx.android.synthetic.main.view_simple_toolbar.tv_title

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class BondsActivity : MVPBaseActivity<Bonds, BondsContract.ActivityView, BondsPresenter>(),
    BondsContract.ActivityView {
  override val layoutId: Int
    get() = R.layout.activity_bonds

  val fragments = arrayListOf<Fragment>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupToolbar(simple_toolbar)
    setNavigationIcon(R.mipmap.back_triangle) {
      close()
    }
    setTitle(string.all_bonds)
    titleColor = R.color.color_4d6599
    setRightMenu(string.filtrate) {
      BondsFiltratePoupWindow(
          this,
          presenter.bonds.delegationDealType,
          presenter.bonds.tradeType,
          presenter.bonds.ageType,
          presenter.bonds.pairs
      ) { delegationDealType, bondDealType, ageType, currency ->
        presenter.bonds = presenter.bonds.copy(
            delegationDealType, bondDealType, ageType, currency
        )
        val bundle = Bundle()
        bundle.putInt(Keys.PARAM_BOND_DELEGATION_TYPE, delegationDealType.value)
        bundle.putInt(Keys.PARAM_BOND_DEAL_TYPE, bondDealType.value)
        bundle.putInt(Keys.PARAM_TRADE_AGE_TYPE, ageType.value)
        bundle.putString(Keys.PARAM_CURRENCY, currency)
        fragments.forEach {
          it.arguments = bundle
          (it as UpdateParamView).onNewParams(bundle)
        }
      }.showAsDropDown(tv_title)
    }
    setRightMenuColor(R.color.color_8fa2cc)
    fragments.add(AttorneyBondsFragment())
    fragments.add(HoldBondsFragment())
    fragments.add(AccountBondsFragment())
    fragments.add(CancelBondsFragment())
    view_pager.adapter = ViewPagerFragmentAdapter(
        supportFragmentManager, fragments, arrayOf(
        getString(string.current_delegation)
        , getString(string.all_bonds)
        , getString(string.bond_settle),
        getString(string.bond_cancel)
    )
    )
    view_pager.offscreenPageLimit = 4
    tab_layout.setupWithViewPager(view_pager)
    tab_layout.getTabAt(0)
        ?.select()
    presenter.getPairs()
  }
}
