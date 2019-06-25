package com.mobilemovement.kotlintvmaze.domain

import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesDomainMapper
import com.mobilemovement.kotlintvmaze.data.SeriesRemoteDataSource
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val seriesDomainMapper: SeriesDomainMapper
) : SeriesRepository {
    override suspend fun searchSeriesAsync(query: String?): List<Series> {
        val series = seriesRemoteDataSource.searchSeriesAsync(query)
        return series.map(seriesDomainMapper::map)
    }
}
