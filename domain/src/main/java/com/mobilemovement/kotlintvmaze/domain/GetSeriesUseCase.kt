package com.mobilemovement.kotlintvmaze.domain

import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.UseCase
import com.mobilemovement.kotlintvmaze.base.util.ErrorFactory
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase.Params
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val seriesViewItemMapper: SeriesViewItemMapper,
    private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<Params, List<SeriesViewItem>> {
    override suspend fun executeAsync(params: Params): Resource<List<SeriesViewItem>> {
        return try {
            val series = seriesRepository.searchSeriesAsync(params.query)
            if (series.isEmpty()) return Resource.empty(errorFactory.createEmptyErrorMessage())
            val mappedSeries = series.map(seriesViewItemMapper::invoke)
            Resource.success(mappedSeries)
        } catch (e: Exception) {
            Resource.error(errorFactory.createApiErrorMessage(e))
        }
    }

    class Params(val query: String?) : UseCase.Params()
}
