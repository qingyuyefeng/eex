package com.eex.mvp.bonds

import com.eex.repository.impl.BondsRepoImpl
import javax.inject.Inject

class AttorneyBondsPresenter @Inject constructor(repo: BondsRepoImpl) : BondsBaseFragmentPresenter(repo)