package com.mobilemovement.kotlintvmaze.domain

import com.mobilemovement.kotlintvmaze.data.Series

interface SeriesRepository {
    suspend fun searchSeriesAsync(query: String?): List<Series>
}
