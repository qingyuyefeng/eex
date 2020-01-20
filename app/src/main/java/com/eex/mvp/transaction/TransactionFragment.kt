package com.eex.mvp.transaction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.eex.R
import com.eex.common.util.LogUtils
import com.eex.common.view.ViewPagerFragmentAdapter
import com.eex.constant.Keys
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.mvp.MVPBaseFragment
import com.eex.mvp.UpdateParamView
import com.eex.mvp.trading.FuturesTradingFragment
import com.eex.mvp.transaction.SpotTradeContract.FragmentWrapperView
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.view_transaction_fail.*

class TransactionFragment : MVPBaseFragment<Spot, FragmentWrapperView, TransactionFragmentPresenter>(),
        FragmentWrapperView {
    val fragments = mutableListOf<UpdateParamView>()

    override val layoutId: Int
        get() = R.layout.fragment_transactions

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_retry.setOnClickListener {
            presenter.pairs()
        }
        presenter.pairs()


    }

    override fun renderViewPager(pair: String) {
        fail.visibility = View.GONE
        val bundle = Bundle()
        bundle.putString(Keys.PARAM_PAIR, pair)
        val spotTradeFragment = SpotTradeFragment()
        spotTradeFragment.arguments = bundle
        val futuresTradingFragment = FuturesTradingFragment()
        futuresTradingFragment.arguments = bundle
        val currencyTradeFragment = CurrencyTradeFragment()
        currencyTradeFragment.arguments = bundle
        fragments.add(spotTradeFragment)
        fragments.add(futuresTradingFragment)
        fragments.add(currencyTradeFragment)
        view_pager.adapter = ViewPagerFragmentAdapter(
                childFragmentManager, arrayListOf(
                spotTradeFragment,
                futuresTradingFragment,
                currencyTradeFragment
        ), arrayOf(
                getString(R.string.spot_transaction), getString(R.string.spot_futures),
                getString(R.string.currency_transaction)
        )
        )
        view_pager.offscreenPageLimit = 3
        tab_layout.setupWithViewPager(view_pager)
        tab_layout.getTabAt(presenter.index)
                ?.select()
    }

    override fun updatePair(pair: String) {
        val bundle = Bundle()
        bundle.putString(Keys.PARAM_PAIR, pair)
        fragments.getOrNull(0)?.run {
            (this as Fragment).arguments = bundle
            onNewParams(bundle)
        }
    }

    override fun updateFuturePair(futerspair: String) {
        val bundle = Bundle()
        bundle.putString(Keys.PARAM_PAIR_DATA, futerspair)
        fragments.getOrNull(1)?.run {
            (this as Fragment).arguments = bundle
            onNewParams(bundle)
        }
    }

    override fun select(index: Int) {
        if (fragments.isNotEmpty()) {
            tab_layout.getTabAt(index)
                ?.select()
        }
    }

    override fun renderFail() {
        fail.visibility = View.VISIBLE
    }
}