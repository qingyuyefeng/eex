package com.eex.mvp.market.marketfrag

import android.content.Intent
import android.os.Bundle
import com.eex.R
import com.eex.common.view.ViewPagerFragmentAdapter
import com.eex.domin.entity.market.Market
import com.eex.mvp.MVPBaseFragment
import com.eex.mvp.market.activity.MarketSearchActivity

import kotlinx.android.synthetic.main.re_frament_trading_marke.*
import kotlinx.android.synthetic.main.re_frament_trading_marke.view_pager
import kotlinx.android.synthetic.main.title_market.*



/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:10
 */
class MarketFragment : MVPBaseFragment<Market, MarketContract.View, MarketPresenter>(), MarketContract.View {


    override val layoutId: Int
        get() = R.layout.re_frament_trading_marke


    override fun hideLoadMore() {
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()


        view_pager.adapter = ViewPagerFragmentAdapter(
                childFragmentManager, arrayListOf(MarketFragment(), MarketFragment(), MarketFragment()
        ), arrayOf("自选", "USDT", "PAX")
        )
        xTablayout.setupWithViewPager(view_pager)
        xTablayout.getTabAt(1)
                ?.select()


    }


    fun initView() {

        market.setOnClickListener {

        }

        sousuo_iv.setOnClickListener {

        }

        sousuo_tv.setText(resources.getString(R.string.market))


        sousuo_image.setOnClickListener {

            startActivity(Intent(context, MarketSearchActivity::class.java))

        }


    }
}