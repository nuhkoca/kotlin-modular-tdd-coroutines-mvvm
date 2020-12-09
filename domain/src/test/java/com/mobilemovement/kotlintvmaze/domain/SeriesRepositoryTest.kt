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

import com.google.common.truth.Truth.assertThat
import com.mobilemovement.kotlintvmaze.data.Image
import com.mobilemovement.kotlintvmaze.data.ImageRaw
import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesDomainMapper
import com.mobilemovement.kotlintvmaze.data.SeriesRaw
import com.mobilemovement.kotlintvmaze.data.SeriesRemoteDataSource
import com.mobilemovement.kotlintvmaze.data.Show
import com.mobilemovement.kotlintvmaze.data.ShowRaw
import com.mobilemovement.kotlintvmaze.shared.test.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesRepository returns list of series`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val dataSource = mockk<SeriesRemoteDataSource>()
            val mapper = mockk<SeriesDomainMapper>()

            coEvery { dataSource.searchSeriesAsync(any()) } returns listOf(
                SeriesRaw(
                    10.0,
                    ShowRaw(
                        1,
                        "testurl",
                        "testname",
                        "testtype",
                        "testlanguage",
                        ImageRaw(
                            "testmedium",
                            "testoriginal"
                        ),
                        "testsummary"
                    )
                )
            )

            coEvery { mapper.invoke(any()) } returns Series(
                10.0,
                Show(
                    1,
                    "testurl",
                    "testname",
                    Image("testoriginal"),
                    "testsummary"
                )
            )

            val repository = SeriesRepositoryImpl(dataSource, mapper)

            repository.searchSeriesAsync(QUERY)

            coVerify { dataSource.searchSeriesAsync(any()) }
            coVerify { mapper.invoke(any()) }

            assertThat(repository.searchSeriesAsync(QUERY)).isNotNull()
            assertThat(repository.searchSeriesAsync(QUERY)).isEqualTo(
                listOf(
                    Series(
                        10.0,
                        Show(
                            1,
                            "testurl",
                            "testname",
                            Image("testoriginal"),
                            "testsummary"
                        )
                    )
                )
            )
        }

    @After
    fun tearDown() {
        // no-op
    }

    companion object {
        private const val QUERY = "boy"
    }
}
