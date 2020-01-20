package com.eex.mvp.histroyattorney

import com.eex.mvp.attorney.AttorneyBasePresenter
import com.eex.repository.impl.AttorneyRepoImpl
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class HistoryAttorneyPresenter @Inject constructor(
  repo: AttorneyRepoImpl
) : AttorneyBasePresenter(repo)
