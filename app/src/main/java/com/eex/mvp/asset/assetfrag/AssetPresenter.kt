package com.eex.mvp.asset.assetfrag

import android.os.Bundle
import com.eex.domin.entity.asset.Asset
import com.eex.mvp.BasePresenterImpl
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:21
 */
class AssetPresenter @Inject constructor(

) :BasePresenterImpl<AssetContract.View,Asset>(), AssetContract.Presenter{
    override fun handleViewState(vo: Asset) {
    }

    override fun initPageState(params:Bundle?) {
    }

}