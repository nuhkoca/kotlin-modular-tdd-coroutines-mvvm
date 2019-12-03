/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobilemovement.kotlintvmaze.di.component

import android.app.Application
import com.mobilemovement.kotlintvmaze.MazeApp
import com.mobilemovement.kotlintvmaze.base.BaseModule
import com.mobilemovement.kotlintvmaze.data.DataModule
import com.mobilemovement.kotlintvmaze.di.module.ActivityBuilder
import com.mobilemovement.kotlintvmaze.di.module.ContextModule
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
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        BaseModule::class,
        ContextModule::class,
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
