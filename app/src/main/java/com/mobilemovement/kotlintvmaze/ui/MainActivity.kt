package com.mobilemovement.kotlintvmaze.ui

import android.view.Menu
import android.view.MenuItem
import com.mobilemovement.kotlintvmaze.R
import com.mobilemovement.kotlintvmaze.base.BaseActivity
import com.mobilemovement.kotlintvmaze.base.util.delegate.ItemAdapter
import com.mobilemovement.kotlintvmaze.base.util.ext.hide
import com.mobilemovement.kotlintvmaze.base.util.ext.init
import com.mobilemovement.kotlintvmaze.base.util.ext.isVisible
import com.mobilemovement.kotlintvmaze.base.util.ext.observeWith
import com.mobilemovement.kotlintvmaze.base.util.ext.show
import com.mobilemovement.kotlintvmaze.base.util.ext.toast
import com.mobilemovement.kotlintvmaze.base.util.ext.unsafeLazy
import kotlinx.android.synthetic.main.activity_main.pbSearch
import kotlinx.android.synthetic.main.activity_main.rvSeries
import kotlinx.android.synthetic.main.activity_main.tietSearch
import kotlinx.android.synthetic.main.activity_main.tilSearch
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var seriesAdapter: ItemAdapter

    private var menu: Menu? = null
    private val searchItem: MenuItem? by unsafeLazy { menu?.findItem(R.id.itemSearch) }

    override fun getViewModelClass() = MainViewModel::class.java

    override val layoutId = R.layout.activity_main

    override fun initView() {
        super.initView()
        rvSeries.init(adapter = seriesAdapter)
        tietSearch.doOnAction(viewModel::getSeries)
    }

    override fun observeViewModel() {
        viewModel.seriesLiveData.observeWith(this) { uiState ->
            uiState.data?.let(seriesAdapter::add)

            if (uiState.isError) {
                toast(uiState.errorMessage)
            }

            pbSearch.isVisible = uiState.isLoading
            tilSearch.isVisible = uiState.isError or uiState.isFirstRun
            rvSeries.isVisible = uiState.isSuccess
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
                rvSeries.hide(); tilSearch.show(); item.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
