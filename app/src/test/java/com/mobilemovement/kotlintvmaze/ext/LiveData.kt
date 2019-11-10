package com.mobilemovement.kotlintvmaze.ext

import androidx.lifecycle.LiveData
import com.mobilemovement.kotlintvmaze.util.OneTimeObserver

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}
