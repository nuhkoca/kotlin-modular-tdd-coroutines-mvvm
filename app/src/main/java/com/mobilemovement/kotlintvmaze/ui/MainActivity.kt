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

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobilemovement.kotlintvmaze.R
import com.mobilemovement.kotlintvmaze.base.util.delegate.ItemAdapter
import com.mobilemovement.kotlintvmaze.base.util.ext.*
import com.mobilemovement.kotlintvmaze.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var seriesAdapter: ItemAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private val binding by viewBinding { ActivityMainBinding.inflate(layoutInflater) }

    private var menu: Menu? = null
    private val searchItem: MenuItem? by unsafeLazy { menu?.findItem(R.id.itemSearch) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        actAsFluid()
        initUi()
        observeViewModel()
    }

    private fun initUi() = with(binding) {
        binding.rvSeries.init(adapter = seriesAdapter)
        binding.tietSearch.doOnAction(viewModel::getSeries)
    }

    private fun observeViewModel() = with(viewModel) {
        seriesLiveData.observeWith(this@MainActivity) { uiState ->
            uiState.data?.let(seriesAdapter::add)

            if (uiState.isError) {
                toast(uiState.errorMessage)
            }

            binding.pbSearch.isVisible = uiState.isLoading
            binding.tilSearch.isVisible = uiState.isError or uiState.isFirstRun
            binding.rvSeries.isVisible = uiState.isSuccess
            searchItem?.isVisible = uiState.isSuccess
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSearch -> {
                binding.rvSeries.hide(); binding.tilSearch.show(); item.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
