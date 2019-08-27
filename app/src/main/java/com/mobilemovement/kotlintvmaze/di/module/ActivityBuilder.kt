package com.mobilemovement.kotlintvmaze.di.module

import com.mobilemovement.kotlintvmaze.di.scope.ActivityScope
import com.mobilemovement.kotlintvmaze.ui.MainActivity
import com.mobilemovement.kotlintvmaze.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}
