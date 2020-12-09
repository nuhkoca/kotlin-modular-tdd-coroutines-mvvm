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

import com.google.common.truth.Truth.assertThat
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
class SeriesRemoteDataSourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesRemoteDataSource fetches list of series`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val api = mockk<MazeService>()

            coEvery { api.searchSeriesAsync(any()) } returns listOf(
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

            val dataSource = SeriesRemoteDataSource(api)

            val invocation = dataSource.searchSeriesAsync(QUERY)

            coVerify { api.searchSeriesAsync(any()) }

            assertThat(invocation).isNotNull()
            assertThat(invocation).isEqualTo(
                listOf(
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
