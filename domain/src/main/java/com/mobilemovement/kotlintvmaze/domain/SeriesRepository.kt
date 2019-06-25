package com.mobilemovement.kotlintvmaze.domain

interface SeriesRepository {
    suspend fun searchSeriesAsync(query: String?): List<Series>
}
