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
import com.mobilemovement.kotlintvmaze.data.*
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
class SeriesViewItemMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesViewItemMapper maps raw data to Series`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val mapper = mockk<SeriesViewItemMapper>()

            val slot = slot<Series>()

            coEvery { mapper.invoke(capture(slot)) } returns
                SeriesViewItem(
                    ShowViewItem(
                        "testname",
                        ImageViewItem("testoriginal"),
                        "testsummary"
                    )
                )

            val invocation = mapper.invoke(
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

            assertThat(invocation).isNotNull()
            assertThat(invocation).isEqualTo(
                SeriesViewItem(
                    ShowViewItem(
                        "testname",
                        ImageViewItem("testoriginal"),
                        "testsummary"
                    )
                )
            )
            assertThat(slot.captured).isEqualTo(
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
        }

    @After
    fun tearDown() {
        // no-op
    }
}
