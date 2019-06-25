package com.mobilemovement.kotlintvmaze.domain

import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    internal abstract fun provideSeriesRepository(seriesRepositoryImpl: SeriesRepositoryImpl): SeriesRepository
}
