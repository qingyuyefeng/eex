package com.eex.mvp.asset.assetfrag

import com.eex.R
import com.eex.domin.entity.asset.Asset
import com.eex.mvp.MVPBaseFragment

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:10
 */
class AssetFragment : MVPBaseFragment<Asset, AssetContract.View, AssetPresenter>(), AssetContract.View {
    override val layoutId: Int
        get() = R.layout.fragment_tra_commonwealfruit

}