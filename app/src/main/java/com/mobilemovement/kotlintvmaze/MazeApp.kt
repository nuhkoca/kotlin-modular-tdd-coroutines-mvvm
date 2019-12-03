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
package com.mobilemovement.kotlintvmaze

import androidx.databinding.DataBindingUtil
import com.mobilemovement.kotlintvmaze.base.BaseApplication
import com.mobilemovement.kotlintvmaze.di.component.DaggerAppComponent
import com.mobilemovement.kotlintvmaze.di.component.DaggerBindingComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MazeApp : BaseApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this).also { appComponent ->
            appComponent.inject(this@MazeApp)

            val bindingComponent =
                DaggerBindingComponent.builder().appComponent(appComponent).build()
            DataBindingUtil.setDefaultComponent(bindingComponent)
        }
    }
}
