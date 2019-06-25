package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.domain.SeriesRaw
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor(private val mazeService: MazeService) : DataSource {
    override suspend fun searchSeriesAsync(query: String?): List<SeriesRaw> =
        mazeService.searchSeriesAsync(query)
}
