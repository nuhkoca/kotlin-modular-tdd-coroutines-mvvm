package com.mobilemovement.kotlintvmaze.base.util.ext

import android.app.Activity
import android.widget.Toast
import com.mobilemovement.kotlintvmaze.base.util.keyboard.FluidContentResizer

fun Activity.actAsFluid() = FluidContentResizer.listen(this)

fun Activity.toast(message: String?) = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
