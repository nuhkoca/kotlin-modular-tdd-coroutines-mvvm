package com.mobilemovement.kotlintvmaze.base.util

import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.Timber.DebugTree
import com.mobilemovement.kotlintvmaze.base.BuildConfig.DEBUG

object TimberFactory {
    fun setupOnDebug() {
        Timber.uprootAll()
        if (DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
