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
package com.mobilemovement.kotlintvmaze.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobilemovement.kotlintvmaze.base.MazeGlide
import com.mobilemovement.kotlintvmaze.base.util.ext.addHttpsPrefix
import javax.inject.Inject

class ImageBindingAdapter @Inject constructor() {
    @BindingAdapter(value = ["android:src"])
    fun ImageView.bindImage(url: String?) {
        if (!url.isNullOrEmpty()) {
            MazeGlide.with(context)
                .asBitmap()
                .load(url.addHttpsPrefix())
                .into(this)
        } else {
            MazeGlide.with(context).clear(this)
        }
    }
}
