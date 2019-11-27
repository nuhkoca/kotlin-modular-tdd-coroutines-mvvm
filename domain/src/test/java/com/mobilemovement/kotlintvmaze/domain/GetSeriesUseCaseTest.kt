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
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.util.ErrorFactory
import com.mobilemovement.kotlintvmaze.data.Image
import com.mobilemovement.kotlintvmaze.data.ImageViewItem
import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.data.Show
import com.mobilemovement.kotlintvmaze.data.ShowViewItem
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase.Params
import com.mobilemovement.kotlintvmaze.shared.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSeriesUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var repository: SeriesRepository

    @MockK
    private lateinit var mapper: SeriesViewItemMapper

    @MockK
    private lateinit var factory: ErrorFactory

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GetSeriesUseCase returns series from remote data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            coEvery { repository.searchSeriesAsync(any()) } returns listOf(
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

            every { mapper.invoke(any()) } returns SeriesViewItem(
                ShowViewItem(
                    "testname",
                    ImageViewItem(
                        "testoriginal"
                    ),
                    "testsummary"
                )
            )

            every { factory.createApiErrorMessage(any()) } returns ""

            val useCase = GetSeriesUseCase(repository, mapper, factory)

            useCase.executeAsync(Params(QUERY))

            coVerify { repository.searchSeriesAsync(any()) }
            verify { mapper.invoke(any()) }

            assertThat(useCase.executeAsync(Params(QUERY))).isNotNull()
            assertThat(useCase.executeAsync(Params(QUERY))).isEqualTo(
                Resource.success(
                    listOf(
                        SeriesViewItem(
                            ShowViewItem(
                                "testname",
                                ImageViewItem(
                                    "testoriginal"
                                ),
                                "testsummary"
                            )
                        )
                    )
                )
            )
        }

    @Test
    fun `GetSeriesUseCase throws error`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        coEvery { repository.searchSeriesAsync(any()) } throws NullPointerException("null")

        every { mapper.invoke(any()) } throws NullPointerException("null")

        every { factory.createApiErrorMessage(any()) } returns "null"

        val useCase = GetSeriesUseCase(repository, mapper, factory)

        useCase.executeAsync(Params(QUERY))

        verify { factory.createApiErrorMessage(any()) }

        assertThat(useCase.executeAsync(Params(QUERY))).isNotNull()
        assertThat(useCase.executeAsync(Params(QUERY))).isEqualTo(
            Resource.error<NullPointerException>(
                "null"
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
