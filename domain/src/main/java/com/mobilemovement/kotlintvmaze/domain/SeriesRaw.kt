package com.mobilemovement.kotlintvmaze.domain

import com.google.gson.annotations.SerializedName

data class SeriesRaw(
    @SerializedName("score") val score: Double?,
    @SerializedName("show") val show: ShowRaw?
)

data class ShowRaw(
    @SerializedName("id") val id: Int?,
    @SerializedName("url") val url: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("image") val image: ImageRaw?,
    @SerializedName("summary") val summary: String?
)

data class ImageRaw(
    @SerializedName("medium") val medium: String?,
    @SerializedName("original") val original: String?
)
