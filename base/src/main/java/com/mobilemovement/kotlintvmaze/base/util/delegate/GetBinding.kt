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
package com.mobilemovement.kotlintvmaze.base.util.delegate

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GetBinding<in V : ViewHolder, out DB : ViewDataBinding>(
    private val itemView: View
) : ReadOnlyProperty<V, DB> {

    private var value: Any? = null

    override fun getValue(thisRef: V, property: KProperty<*>): DB {
        if (value == null) {
            value = DataBindingUtil.getBinding(itemView)
        }
        @Suppress("UNCHECKED_CAST")
        return value as DB
    }
}

fun <V : ViewHolder, DB : ViewDataBinding> getBinding(itemView: View) =
    GetBinding<V, DB>(itemView)
