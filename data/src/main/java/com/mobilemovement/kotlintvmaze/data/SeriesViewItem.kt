package com.mobilemovement.kotlintvmaze.data

data class SeriesViewItem(
    val show: ShowViewItem?
)

data class ShowViewItem(
    val name: String?,
    val image: ImageViewItem?,
    val summary: String?
)

data class ImageViewItem(
    val original: String?
)
