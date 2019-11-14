package com.mobilemovement.kotlintvmaze

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.data.ImageViewItem
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.data.ShowViewItem
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase
import com.mobilemovement.kotlintvmaze.shared.test.CoroutinesTestRule
import com.mobilemovement.kotlintvmaze.shared.test.observeOnce
import com.mobilemovement.kotlintvmaze.ui.MainViewModel
import com.mobilemovement.kotlintvmaze.ui.MainViewModel.SeriesUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val observer = mockk<Observer<SeriesUiState>>(relaxed = true)

    private lateinit var getSeriesUseCase: GetSeriesUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() = coroutinesTestRule.testDispatcher.runBlockingTest {
        getSeriesUseCase = mockk()

        mockkObject(SeriesUiState())

        coEvery { getSeriesUseCase.executeAsync(any()) } returns Resource.success(
            listOf(
                SeriesViewItem(
                    ShowViewItem(
                        any(),
                        ImageViewItem(
                            any()
                        ),
                        any()
                    )
                )
            )
        )

        viewModel = MainViewModel(getSeriesUseCase)
    }

    @Test
    fun `Series will be fetched successfully`() {
        viewModel.seriesLiveData.observeForever(observer)

        viewModel.getSeries(any())

        coVerify(exactly = 1) { getSeriesUseCase.executeAsync(any()) }

        viewModel.seriesLiveData.observeOnce {
            assertThat(it.data).isEqualTo(
                listOf(
                    SeriesViewItem(
                        ShowViewItem(
                            any(),
                            ImageViewItem(
                                any()
                            ),
                            any()
                        )
                    )
                )
            )
        }
    }

    @After
    fun tearDown() {
        viewModel.seriesLiveData.removeObserver(observer)
    }
}
