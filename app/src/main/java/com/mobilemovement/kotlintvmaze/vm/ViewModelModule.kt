package com.mobilemovement.kotlintvmaze.vm

import androidx.lifecycle.ViewModelProvider
import com.mobilemovement.kotlintvmaze.ui.MainViewModelModule
import dagger.Binds
import dagger.Module

@Module(includes = [MainViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsMazeViewModelFactory(mazeViewModelFactory: MazeViewModelFactory):
        ViewModelProvider.Factory
}
