package com.mobilemovement.kotlintvmaze.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.util.coroutines.DefaultDispatcherProvider
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase
import com.mobilemovement.kotlintvmaze.domain.GetSeriesUseCase.Params
import com.mobilemovement.kotlintvmaze.domain.SeriesViewItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getSeriesUseCase: GetSeriesUseCase) : ViewModel() {

    private val _seriesLiveData = MutableLiveData<Resource<List<SeriesViewItem>>>()
    val seriesLiveData: LiveData<Resource<List<SeriesViewItem>>> get() = _seriesLiveData

    fun getSeries(query: String?) {
        viewModelScope.launch(DefaultDispatcherProvider.io()) {
            _seriesLiveData.postValue(Resource.loading())
            _seriesLiveData.postValue(getSeriesUseCase.executeAsync(Params(query)))
        }
    }
}
