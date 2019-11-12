package com.mobilemovement.kotlintvmaze.base.util.ext

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ViewModelProvider.Factory.get(fragmentActivity: FragmentActivity): T =
    ViewModelProvider(fragmentActivity, this)[T::class.java]
