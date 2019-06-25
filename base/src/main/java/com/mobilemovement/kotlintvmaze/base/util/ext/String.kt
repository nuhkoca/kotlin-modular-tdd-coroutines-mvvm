package com.mobilemovement.kotlintvmaze.base.util.ext

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.Spanned

private const val HTTPS_PREFIX = "https://"

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    return if (VERSION.SDK_INT >= VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.addHttpsPrefix(): String {
    return if (startsWith(HTTPS_PREFIX)) {
        this
    } else {
        replace("^http?://".toRegex(), HTTPS_PREFIX)
    }
}
