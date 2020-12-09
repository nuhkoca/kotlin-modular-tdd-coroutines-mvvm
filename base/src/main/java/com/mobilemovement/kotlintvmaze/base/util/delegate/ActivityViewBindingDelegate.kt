/*
 * Copyright 2020 nuhkoca.com
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
package com.mobilemovement.kotlintvmaze.base.util.delegate

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A [ReadOnlyProperty] to create a [ViewBinding] for requested [AppCompatActivity]
 *
 * @param activity The activity that has [ViewBinding] enabled
 * @param viewBindingFactory The factory to initialize binding
 */
class ActivityViewBindingDelegate<out T : ViewBinding>(
    private val activity: AppCompatActivity,
    private val viewBindingFactory: (LayoutInflater) -> T
) : ReadOnlyProperty<AppCompatActivity, T>, LifecycleEventObserver {
    private var binding: T? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = activity.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Activity views are destroyed.")
        }

        return viewBindingFactory(thisRef.layoutInflater).also {
            this@ActivityViewBindingDelegate.binding = it
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            binding = null
            activity.lifecycle.removeObserver(this)
        }
    }
}
