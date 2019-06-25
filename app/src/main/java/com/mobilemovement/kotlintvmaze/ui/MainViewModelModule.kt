package com.mobilemovement.kotlintvmaze.ui

import androidx.lifecycle.ViewModel
import com.mobilemovement.kotlintvmaze.vm.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
