package com.mobilemovement.kotlintvmaze.base

import com.mobilemovement.kotlintvmaze.base.util.DefaultErrorFactory
import com.mobilemovement.kotlintvmaze.base.util.ErrorFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseModule {

    @Binds
    abstract fun provideErrorFactory(defaultErrorFactory: DefaultErrorFactory): ErrorFactory
}
