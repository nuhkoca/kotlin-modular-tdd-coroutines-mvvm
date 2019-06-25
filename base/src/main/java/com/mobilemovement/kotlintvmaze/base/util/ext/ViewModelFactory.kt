package com.mobilemovement.kotlintvmaze.base.util.ext

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> ViewModelProvider.Factory.get(fragmentActivity: FragmentActivity): T =
    ViewModelProviders.of(fragmentActivity, this)[T::class.java]
