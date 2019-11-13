package com.mobilemovement.kotlintvmaze.data

import com.google.common.truth.Truth.assertThat
import com.mobilemovement.kotlintvmaze.data.util.CoroutinesTestRule
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

                        "testtype", "testlanguage",
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
