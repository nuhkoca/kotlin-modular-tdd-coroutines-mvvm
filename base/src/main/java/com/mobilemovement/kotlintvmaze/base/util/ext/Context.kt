package com.mobilemovement.kotlintvmaze.base.util.ext

import android.content.Context
import android.view.LayoutInflater

inline val Context.getInflater: LayoutInflater
    get() = LayoutInflater.from(this)
