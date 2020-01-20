package com.eex.mvp.attorney

import android.os.Bundle
import com.eex.R
import com.eex.common.view.ViewPagerFragmentAdapter
import com.eex.domin.entity.attorney.Attorney
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.activity_attorney_list.tab_layout
import kotlinx.android.synthetic.main.activity_attorney_list.view_pager
import kotlinx.android.synthetic.main.view_simple_toolbar.simple_toolbar

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class AttorneyActivity : MVPBaseActivity<Attorney, AttorneyContract.ActivityView, AttorneyActivityPresenter>(),
  AttorneyContract.ActivityView {
  override val layoutId: Int
    get() = R.layout.activity_attorney_list

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupToolbar(simple_toolbar)
    setNavigationIcon(R.mipmap.back_triangle) {
      close()
    }
    setTitle(R.string.current_delegation)
    titleColor = R.color.color_4d6599
    setRightMenu(R.string.history_delegation) {
      Navigator.toHistoryAttorneyActivity(this)
    }
    setRightMenuColor(R.color.color_8fa2cc)
    view_pager.adapter = ViewPagerFragmentAdapter(
        supportFragmentManager, arrayListOf(
        AttorneyNormalFragment(),
        AttorneyLimitedFragment()
    ), arrayOf(getString(R.string.attorney_normal), getString(R.string.attorney_limit))
    )
    tab_layout.setupWithViewPager(view_pager)
    tab_layout.getTabAt(0)
        ?.select()
  }

}
