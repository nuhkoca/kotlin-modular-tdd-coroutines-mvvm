package com.mobilemovement.kotlintvmaze.data

interface DataSource {
    suspend fun searchSeriesAsync(query: String?): List<SeriesRaw>
}
