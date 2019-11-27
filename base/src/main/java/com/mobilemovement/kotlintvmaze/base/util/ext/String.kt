/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobilemovement.kotlintvmaze.base.util.ext

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.Spanned

private const val HTTPS_PREFIX = "https://"
private const val HTTP_REGEX = "^http?://"

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
        replace(HTTP_REGEX.toRegex(), HTTPS_PREFIX)
    }
}
