package com.mobilemovement.kotlintvmaze.data

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMazeService(retrofit: Retrofit): MazeService = retrofit.create()
}
