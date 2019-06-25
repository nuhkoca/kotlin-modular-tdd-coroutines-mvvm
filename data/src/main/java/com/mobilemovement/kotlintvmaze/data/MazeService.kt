package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.domain.SeriesRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface MazeService {

    @GET("search/shows")
    suspend fun searchSeriesAsync(@Query("q") query: String?): List<SeriesRaw>
}
