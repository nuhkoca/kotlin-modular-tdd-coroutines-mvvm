package com.mobilemovement.kotlintvmaze.base.util.keyboard

data class KeyboardVisibilityChanged(
    val visible: Boolean,
    val contentHeight: Int,
    val contentHeightBeforeResize: Int
)
