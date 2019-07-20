package com.mobilemovement.kotlintvmaze.base

import android.app.Activity
import com.mobilemovement.kotlintvmaze.base.util.TimberFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class BaseApplication : DaggerApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        TimberFactory.setupOnDebug()
    }

    abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}
