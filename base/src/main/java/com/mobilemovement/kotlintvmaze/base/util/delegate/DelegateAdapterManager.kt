package com.mobilemovement.kotlintvmaze.base.util.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class DelegateAdapterManager @Inject constructor(
    private val delegateAdapters: List<@JvmSuppressWildcards DelegateAdapter>
) {
    fun createViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder =
        delegateAdapters[type].createViewHolder(parent)

    fun bindViewHolder(type: Int, holder: RecyclerView.ViewHolder, item: AdapterItem) =
        delegateAdapters[type].bindViewHolder(holder, item)

    fun getViewType(item: AdapterItem): Int {
        return delegateAdapters.indexOfFirst { delegateAdapter ->
            delegateAdapter.isForViewType(item)
        }
    }
}
