package com.mobilemovement.kotlintvmaze.data

data class Series(
    val score: Double?,
    val show: Show?
)

data class Show(
    val id: Int?,
    val url: String?,
    val name: String?,
    val image: Image?,
    val summary: String?
)

data class Image(
    val original: String?
)
