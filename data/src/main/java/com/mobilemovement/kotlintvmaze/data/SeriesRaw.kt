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
package com.mobilemovement.kotlintvmaze.data

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
