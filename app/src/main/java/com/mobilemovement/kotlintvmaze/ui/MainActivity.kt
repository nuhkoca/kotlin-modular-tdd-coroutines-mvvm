package com.mobilemovement.kotlintvmaze.ui

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import com.mobilemovement.kotlintvmaze.R
import com.mobilemovement.kotlintvmaze.base.BaseActivity
import com.mobilemovement.kotlintvmaze.base.Status.EMPTY
import com.mobilemovement.kotlintvmaze.base.Status.ERROR
import com.mobilemovement.kotlintvmaze.base.Status.LOADING
import com.mobilemovement.kotlintvmaze.base.Status.SUCCESS
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
    lateinit var context: Context

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
        viewModel.seriesLiveData.observeWith(this) { resource ->
            when (resource.status) {
                SUCCESS -> resource.data?.let(seriesAdapter::add)
                ERROR, EMPTY -> toast(resource.message)
                else -> { /* no-op */
                }
            }
            with(resource.status) {
                pbSearch.isVisible = this == LOADING
                tilSearch.isVisible = this == ERROR || this == EMPTY
                rvSeries.isVisible = this == SUCCESS
                searchItem?.isVisible = this == SUCCESS
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemSearch -> {
                rvSeries.hide()
                tilSearch.show()
                item.isVisible = false
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
