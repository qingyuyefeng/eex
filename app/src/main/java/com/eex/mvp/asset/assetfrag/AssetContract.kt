package com.eex.mvp.asset.assetfrag

import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:17
 */
class AssetContract {
    interface View:BaseView{

    }
    interface Presenter:BasePresenter<View>
}