/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
