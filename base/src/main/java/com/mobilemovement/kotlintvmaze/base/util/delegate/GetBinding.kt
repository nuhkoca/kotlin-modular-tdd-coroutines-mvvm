package com.mobilemovement.kotlintvmaze.base.util.delegate

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GetBinding<in V : ViewHolder, out DB : ViewDataBinding>(
    private val itemView: View
) : ReadOnlyProperty<V, DB?> {

    private var value: DB? = null

    override fun getValue(thisRef: V, property: KProperty<*>): DB? {
        value = value ?: DataBindingUtil.getBinding(itemView)
        return value
    }
}

fun <V : ViewHolder, DB : ViewDataBinding> getBinding(itemView: View) =
    GetBinding<V, DB>(itemView)
