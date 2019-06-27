package com.mobilemovement.kotlintvmaze.base.util.ext

import android.content.Context
import android.view.LayoutInflater

inline val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
