package com.mobilemovement.kotlintvmaze.base.util.ext

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager

inline var View.isVisible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

inline var View.isHidden: Boolean
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

inline var View.isInvisible: Boolean
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

fun View.show() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.hideKeyBoard() {
    val inputMethodManager =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
