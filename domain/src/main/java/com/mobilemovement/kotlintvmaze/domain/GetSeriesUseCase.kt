package com.mobilemovement.kotlintvmaze.domain

import android.content.Context
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.UseCase
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase.Params
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val seriesViewItemMapper: SeriesViewItemMapper,
    private val context: Context
) : UseCase.ResourceUseCase<Params, List<SeriesViewItem>> {
    override suspend fun executeAsync(params: Params): Resource<List<SeriesViewItem>> {
        return try {
            val series = seriesRepository.searchSeriesAsync(params.query)
            if (series.isEmpty()) return Resource.empty(context.getString(R.string.empty_message))
            val mappedSeries = series.map(seriesViewItemMapper::map)
            Resource.success(mappedSeries)
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    class Params(val query: String?) : UseCase.Params()
}
