package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.domain.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    internal abstract fun provideSeriesRepository(seriesRepositoryImpl: SeriesRepositoryImpl): SeriesRepository

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideMazeService(retrofit: Retrofit): MazeService = retrofit.create()
    }
}
