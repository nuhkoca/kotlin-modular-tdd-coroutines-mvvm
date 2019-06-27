package com.mobilemovement.kotlintvmaze

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.util.DefaultErrorFactory
import com.mobilemovement.kotlintvmaze.data.MazeService
import com.mobilemovement.kotlintvmaze.data.SeriesDomainMapper
import com.mobilemovement.kotlintvmaze.data.SeriesRemoteDataSource
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase
import com.mobilemovement.kotlintvmaze.domain.SeriesRepositoryImpl
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
    lateinit var mazeService: MazeService
    @Mock
    lateinit var seriesViewItemMapper: SeriesViewItemMapper
    @Mock
    lateinit var seriesDomainMapper: SeriesDomainMapper
    @Mock
    lateinit var context: Context

    private val observer = mock(Observer::class.java) as
            Observer<Resource<List<SeriesViewItem>>>

    private lateinit var getSeriesUseCase: GetSeriesUseCase
    private lateinit var mainViewModel: MainViewModel
    private lateinit var seriesRepository: SeriesRepositoryImpl
    private lateinit var seriesRemoteDataSource: SeriesRemoteDataSource
    private lateinit var defaultErrorFactory: DefaultErrorFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        seriesRemoteDataSource = SeriesRemoteDataSource(mazeService)
        seriesRepository = SeriesRepositoryImpl(seriesRemoteDataSource, seriesDomainMapper)
        defaultErrorFactory = DefaultErrorFactory(context)
        getSeriesUseCase = GetSeriesUseCase(seriesRepository, seriesViewItemMapper, defaultErrorFactory)
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
