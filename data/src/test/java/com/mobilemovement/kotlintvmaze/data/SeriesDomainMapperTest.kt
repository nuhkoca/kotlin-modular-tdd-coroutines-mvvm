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

import com.google.common.truth.Truth
import com.mobilemovement.kotlintvmaze.shared.test.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesDomainMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesViewItemMapper maps raw data to Series`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val mapper = mockk<SeriesDomainMapper>()

            val slot = slot<SeriesRaw>()

            coEvery { mapper.invoke(capture(slot)) } returns
                Series(
                    10.0,
                    Show(
                        1,
                        "testname",
                        "testurl",
                        Image("testoriginal"),
                        "testsummary"
                    )
                )

            val invocation = mapper.invoke(
                SeriesRaw(
                    10.0,
                    ShowRaw(
                        1,
                        "testurl",
                        "testname",
                        "testype",
                        "testlanguage",
                        ImageRaw(
                            "testmedium",
                            "testoriginal"
                        ),
                        "testsummary"
                    )
                )
            )

            Truth.assertThat(invocation).isNotNull()
            Truth.assertThat(invocation).isEqualTo(
                Series(
                    10.0,
                    Show(
                        1,
                        "testname",
                        "testurl",
                        Image("testoriginal"),
                        "testsummary"
                    )
                )
            )
            Truth.assertThat(slot.captured).isEqualTo(
                SeriesRaw(
                    10.0,
                    ShowRaw(
                        1,
                        "testurl",
                        "testname",
                        "testype",
                        "testlanguage",
                        ImageRaw(
                            "testmedium",
                            "testoriginal"
                        ),
                        "testsummary"
                    )
                )
            )
        }

    @After
    fun tearDown() {
        // no-op
    }
}
