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
package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.base.util.coroutines.DefaultDispatcherProvider
import com.mobilemovement.kotlintvmaze.base.util.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesDomainMapper @Inject constructor() : Mapper<SeriesRaw, Series> {
    override suspend fun invoke(input: SeriesRaw) =
        withContext(DefaultDispatcherProvider.default()) {
            return@withContext with(input) {
                val image = Image(show?.image?.original)
                val show = Show(show?.id, show?.url, show?.name, image, show?.summary)

                Series(score, show)
            }
        }
}
