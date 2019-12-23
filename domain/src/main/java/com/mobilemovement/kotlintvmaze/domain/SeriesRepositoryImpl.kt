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
        return series.map { seriesDomainMapper.invoke(it) }
    }
}
