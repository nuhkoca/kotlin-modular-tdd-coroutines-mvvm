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

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ItemAdapter @Inject constructor(private val delegateAdapterManager: DelegateAdapterManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapterManager.createViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateAdapterManager.getViewType(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return delegateAdapterManager.bindViewHolder(
            getItemViewType(position),
            holder,
            items[position]
        )
    }

    fun add(items: List<AdapterItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
