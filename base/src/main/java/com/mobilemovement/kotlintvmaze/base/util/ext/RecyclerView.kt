package com.mobilemovement.kotlintvmaze.base.util.ext

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

fun RecyclerView.init(
    adapter: Adapter<*>,
    hasFixedSize: Boolean = false
): RecyclerView {
    setHasFixedSize(hasFixedSize)
    setAdapter(adapter)
    return this
}
