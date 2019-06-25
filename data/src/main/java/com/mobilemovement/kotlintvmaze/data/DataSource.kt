package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.domain.SeriesRaw

interface DataSource {
    suspend fun searchSeriesAsync(query: String?): List<SeriesRaw>
}
