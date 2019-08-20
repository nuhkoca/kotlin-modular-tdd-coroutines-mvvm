package com.mobilemovement.kotlintvmaze.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseViewHolder<DB : ViewDataBinding, T : Any>(itemView: View) :
    ViewHolder(itemView) {

    protected var dataBinding: DB? = null

    init {
        dataBinding = DataBindingUtil.getBinding(itemView)
    }

    abstract fun bindTo(item: T)
}
