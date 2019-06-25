package com.mobilemovement.kotlintvmaze

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.data.MazeService
import com.mobilemovement.kotlintvmaze.data.SeriesDomainMapper
import com.mobilemovement.kotlintvmaze.data.SeriesRemoteDataSource
import com.mobilemovement.kotlintvmaze.data.SeriesRepositoryImpl
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase
import com.mobilemovement.kotlintvmaze.domain.SeriesViewItem
import com.mobilemovement.kotlintvmaze.domain.SeriesViewItemMapper
import com.mobilemovement.kotlintvmaze.ui.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    val mazeService: MazeService = mock(MazeService::class.java)
    @Mock
    private val seriesViewItemMapper: SeriesViewItemMapper = mock(SeriesViewItemMapper::class.java)
    @Mock
    private val seriesDomainMapper: SeriesDomainMapper = mock(SeriesDomainMapper::class.java)

    private val context = mock(Context::class.java)

    private val observer = mock(Observer::class.java) as
            Observer<Resource<List<SeriesViewItem>>>

    private lateinit var getSeriesUseCase: GetSeriesUseCase
    private lateinit var mainViewModel: MainViewModel
    private lateinit var seriesRepository: SeriesRepositoryImpl
    private lateinit var seriesRemoteDataSource: SeriesRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        seriesRemoteDataSource = SeriesRemoteDataSource(mazeService)
        seriesRepository = SeriesRepositoryImpl(seriesRemoteDataSource, seriesDomainMapper)
        getSeriesUseCase = GetSeriesUseCase(seriesRepository, seriesViewItemMapper, context)
        mainViewModel = MainViewModel(getSeriesUseCase)
    }

    @Test
    fun `series_should_be_fetched_successfully`() = runBlocking {
        mainViewModel.seriesLiveData.observeForever(observer)

        mainViewModel.getSeries(QUERY)

        mainViewModel.seriesLiveData.observeOnce { Truth.assertThat(it.data).isNotNull() }
    }

    @After
    fun tearDown() {
        mainViewModel.seriesLiveData.removeObserver(observer)
    }

    companion object {
        private const val QUERY = "girl"
    }
}
