package com.mobilemovement.kotlintvmaze.di.component

import android.app.Application
import com.mobilemovement.kotlintvmaze.MazeApp
import com.mobilemovement.kotlintvmaze.data.DataModule
import com.mobilemovement.kotlintvmaze.di.module.ActivityBuilder
import com.mobilemovement.kotlintvmaze.di.module.ContextModule
import com.mobilemovement.kotlintvmaze.di.module.NetworkModule
import com.mobilemovement.kotlintvmaze.domain.DomainModule
import com.mobilemovement.kotlintvmaze.vm.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ContextModule::class,
        NetworkModule::class,
        ActivityBuilder::class,
        ViewModelModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent : AndroidInjector<MazeApp> {

    override fun inject(instance: MazeApp?)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
