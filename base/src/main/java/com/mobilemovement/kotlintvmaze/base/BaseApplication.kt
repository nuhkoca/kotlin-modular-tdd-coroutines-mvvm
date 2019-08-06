package com.mobilemovement.kotlintvmaze.base

import com.mobilemovement.kotlintvmaze.base.util.TimberFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class BaseApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        TimberFactory.setupOnDebug()
    }

    abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>

    override fun androidInjector() = dispatchingAndroidInjector
}
