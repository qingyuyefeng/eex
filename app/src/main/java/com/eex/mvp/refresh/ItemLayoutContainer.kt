package com.eex.mvp.refresh

import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class ItemLayoutContainer<ITEM>(override val containerView: View) : LayoutContainer {
  abstract fun bindItem(item: ITEM)
}