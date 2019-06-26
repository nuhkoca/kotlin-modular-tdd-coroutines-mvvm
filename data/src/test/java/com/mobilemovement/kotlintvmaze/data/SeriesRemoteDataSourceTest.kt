package com.mobilemovement.kotlintvmaze.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class SeriesRemoteDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mazeService: MazeService

    private lateinit var seriesRemoteDataSource: SeriesRemoteDataSource

    lateinit var fakeSeriesRaw: SeriesRaw

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        seriesRemoteDataSource = SeriesRemoteDataSource(mazeService)
        fakeSeriesRaw = SeriesRaw(1.2, null)
    }

    @Test
    fun `series_remote_data_source_should_fetch_data_successfully`() {
        runBlocking {
            Mockito.`when`(mazeService.searchSeriesAsync(DEFAULT_QUERY))
                .thenReturn(ArgumentMatchers.anyList())

            Truth.assertThat(seriesRemoteDataSource.searchSeriesAsync(DEFAULT_QUERY)).isNotNull()
        }
    }

    @Test
    fun `series_remote_data_source_should_return_null_data`() {
        runBlocking {
            Mockito.`when`(mazeService.searchSeriesAsync(DEFAULT_QUERY))
                .thenReturn(null)

            Truth.assertThat(seriesRemoteDataSource.searchSeriesAsync(DEFAULT_QUERY)).isNull()
        }
    }

    @After
    fun tearDown() {
        // no-op
    }

    companion object {
        private const val DEFAULT_QUERY = "girl"
    }
}
