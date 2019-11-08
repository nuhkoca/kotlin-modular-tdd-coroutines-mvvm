package com.mobilemovement.kotlintvmaze.base.util

import android.os.Looper

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun <T> checkMainThread(block: () -> T): T =
    if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalStateException("Object initialized on main thread.")
    } else {
        block()
    }
