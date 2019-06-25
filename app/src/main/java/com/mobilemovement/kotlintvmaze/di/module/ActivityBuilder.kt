package com.mobilemovement.kotlintvmaze.di.module

import com.mobilemovement.kotlintvmaze.di.scope.ActivityScope
import com.mobilemovement.kotlintvmaze.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}
