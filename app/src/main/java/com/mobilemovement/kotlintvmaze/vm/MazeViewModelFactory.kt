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
package com.mobilemovement.kotlintvmaze.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST", "TooGenericExceptionThrown", "SimpleRedundantLet")
@Singleton
class MazeViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val result: Provider<out ViewModel>? = creators[modelClass] ?: creators.entries
            .firstOrNull { modelClass.isAssignableFrom(it.key) }
            ?.let { it.value }
        ?: throw IllegalArgumentException("$UNKNOWN_MODEL_EXCEPTION $modelClass")

        return result?.get() as? T ?: throw RuntimeException(EXCEPTION_MESSAGE)
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Can not get view model from factory"
        private const val UNKNOWN_MODEL_EXCEPTION = "Unknown model class"
    }
}
