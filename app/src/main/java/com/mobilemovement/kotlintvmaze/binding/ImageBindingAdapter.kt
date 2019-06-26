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
