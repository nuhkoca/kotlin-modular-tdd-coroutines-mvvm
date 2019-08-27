package com.mobilemovement.kotlintvmaze.ui

import com.mobilemovement.kotlintvmaze.base.util.delegate.DelegateAdapter
import com.mobilemovement.kotlintvmaze.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
object MainActivityModule {

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideSeriesAdapterDelegates(): List<DelegateAdapter> {
        return listOf(SeriesAdapter())
    }
}
