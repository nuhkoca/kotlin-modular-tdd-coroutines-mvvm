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
package com.mobilemovement.kotlintvmaze.base.util

import android.os.Looper

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun <T> checkMainThread(block: () -> T): T =
    if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalStateException("Object initialized on main thread.")
    } else {
        block()
    }
