package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.domain.Series
import com.mobilemovement.kotlintvmaze.domain.SeriesRepository
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
