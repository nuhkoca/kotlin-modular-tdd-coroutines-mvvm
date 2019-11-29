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
package com.mobilemovement.kotlintvmaze.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilemovement.kotlintvmaze.base.Status.ERROR
import com.mobilemovement.kotlintvmaze.base.Status.LOADING
import com.mobilemovement.kotlintvmaze.base.Status.SUCCESS
import com.mobilemovement.kotlintvmaze.base.Status.EMPTY
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
