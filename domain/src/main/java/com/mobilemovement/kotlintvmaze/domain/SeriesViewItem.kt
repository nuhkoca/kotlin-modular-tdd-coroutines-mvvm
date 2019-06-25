package com.mobilemovement.kotlintvmaze.domain

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
