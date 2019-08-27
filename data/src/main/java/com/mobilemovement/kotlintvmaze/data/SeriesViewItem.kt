package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.base.util.delegate.AdapterItem

data class SeriesViewItem(
    val show: ShowViewItem?
) : AdapterItem

data class ShowViewItem(
    val name: String?,
    val image: ImageViewItem?,
    val summary: String?
)

data class ImageViewItem(
    val original: String?
)
