package com.eex.di.modules;

import com.eex.mvp.asset.assetfrag.AssetFragment;
import com.eex.mvp.attorney.AttorneyLimitedFragment;
import com.eex.mvp.attorney.AttorneyNormalFragment;

import com.eex.mvp.bonds.AccountBondsFragment;
import com.eex.mvp.bonds.AttorneyBondsFragment;
import com.eex.mvp.bonds.BondsFragment;
import com.eex.mvp.bonds.CancelBondsFragment;
import com.eex.mvp.bonds.HoldBondsFragment;
import com.eex.mvp.homefrag.HomeFragment;
import com.eex.mvp.market.marketfrag.MarketFragment;
import com.eex.mvp.mine.mainfrag.MineFragment;
import com.eex.mvp.trading.tradingfrag.TradingFragment;

import com.eex.mvp.transaction.CurrencyTradeFragment;
import com.eex.mvp.transaction.SpotTradeFragment;

import com.eex.mvp.transaction.TransactionFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllFragmentsModule {
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragmentInjector();
    @ContributesAndroidInjector
    abstract MarketFragment contributeMarketFragmentInjector();
    @ContributesAndroidInjector
    abstract TradingFragment contributeTradingFragmentInjector();
    @ContributesAndroidInjector
    abstract AssetFragment contributeAssetFragmentInjector();
    @ContributesAndroidInjector
    abstract MineFragment contributeMineFragmentInjector();


    @ContributesAndroidInjector
    abstract AttorneyNormalFragment contributeAttorneyNormalFragmentInjector();

    @ContributesAndroidInjector
    abstract AttorneyLimitedFragment contributeAttorneyLimitedFragmentInjector();

    @ContributesAndroidInjector
    abstract TransactionFragment contributeTransactionFragmentInjector();

    @ContributesAndroidInjector
    abstract SpotTradeFragment contributeSpotTradeFragmentInjector();

    @ContributesAndroidInjector
    abstract CurrencyTradeFragment contributeCurrencyTradeFragmentInjector();

    @ContributesAndroidInjector
    abstract AttorneyBondsFragment contributeAttorneyBondsFragmentInjector();

    @ContributesAndroidInjector
    abstract HoldBondsFragment contributeHoldBondsFragmentInjector();

    @ContributesAndroidInjector
    abstract AccountBondsFragment contributeAccountBondsFragmentInjector();

    @ContributesAndroidInjector
    abstract CancelBondsFragment contributeCancelBondsFragmentInjector();
}
