package com.mobilemovement.kotlintvmaze.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobilemovement.kotlintvmaze.base.util.ext.actAsFluid
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel> : DaggerAppCompatActivity(), BaseView {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = viewModelFactory.create(getViewModelClass())
        initView()
        observeViewModel()
    }

    open fun initView() {
        actAsFluid()
    }

    abstract override fun observeViewModel()
}
