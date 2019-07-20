package com.mobilemovement.kotlintvmaze.base.util

import com.mobilemovement.kotlintvmaze.base.BuildConfig.DEBUG
import timber.log.Timber
import timber.log.Timber.DebugTree

object TimberFactory {
    fun setupOnDebug() {
        Timber.uprootAll()
        if (DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
