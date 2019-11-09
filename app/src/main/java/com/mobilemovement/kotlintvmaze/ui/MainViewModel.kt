package com.mobilemovement.kotlintvmaze.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilemovement.kotlintvmaze.base.Status.EMPTY
import com.mobilemovement.kotlintvmaze.base.Status.ERROR
import com.mobilemovement.kotlintvmaze.base.Status.LOADING
import com.mobilemovement.kotlintvmaze.base.Status.SUCCESS
import com.mobilemovement.kotlintvmaze.base.util.coroutines.DefaultDispatcherProvider
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase.Params
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getSeriesUseCase: GetSeriesUseCase) :
    ViewModel() {

    private val _seriesLiveData = MutableLiveData<SeriesUiState>()
    val seriesLiveData: LiveData<SeriesUiState> get() = _seriesLiveData

    init {
        _seriesLiveData.value = SeriesUiState()
    }

    fun getSeries(query: String?) {
        viewModelScope.launch(DefaultDispatcherProvider.io()) {
            _seriesLiveData.postValue(
                getCurrentSeriesUiState?.copy(isLoading = true, isFirstRun = false)
            )

            val series = getSeriesUseCase.executeAsync(Params(query))

            _seriesLiveData.postValue(
                getCurrentSeriesUiState?.copy(
                    data = series.data,
                    isSuccess = series.status == SUCCESS,
                    isLoading = series.status == LOADING,
                    isError = series.status == ERROR || series.status == EMPTY,
                    errorMessage = series.message,
                    isFirstRun = false
                )
            )
        }
    }

    private val getCurrentSeriesUiState = _seriesLiveData.value

    data class SeriesUiState(
        val data: List<SeriesViewItem>? = null,
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String? = null,
        val isFirstRun: Boolean = true
    )
}
