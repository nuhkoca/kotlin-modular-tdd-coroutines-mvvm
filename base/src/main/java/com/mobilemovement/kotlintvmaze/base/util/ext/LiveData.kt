package com.mobilemovement.kotlintvmaze.base.util.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T : Any> LiveData<T>.observeWith(
    lifecycleOwner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
) {
    observe(lifecycleOwner, Observer {
        it ?: return@Observer
        onChanged.invoke(it)
    })
}
