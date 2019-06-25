package com.mobilemovement.kotlintvmaze

import androidx.databinding.DataBindingUtil
import com.mobilemovement.kotlintvmaze.BuildConfig.DEBUG
import com.mobilemovement.kotlintvmaze.di.component.DaggerAppComponent
import com.mobilemovement.kotlintvmaze.di.component.DaggerBindingComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class MazeApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        if (DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this).also { appComponent ->
            appComponent.inject(this@MazeApp)

            val bindingComponent = DaggerBindingComponent.builder().appComponent(appComponent).build()
            DataBindingUtil.setDefaultComponent(bindingComponent)
        }
    }
}
