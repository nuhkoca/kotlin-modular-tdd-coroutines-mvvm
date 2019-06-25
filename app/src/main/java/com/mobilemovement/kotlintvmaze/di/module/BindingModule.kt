package com.mobilemovement.kotlintvmaze.di.module

import com.mobilemovement.kotlintvmaze.binding.ImageBindingAdapter
import com.mobilemovement.kotlintvmaze.di.scope.BindingScope
import dagger.Module
import dagger.Provides

@Module
class BindingModule {

    @BindingScope
    @Provides
    internal fun provideImageBindingAdapter() = ImageBindingAdapter()
}
