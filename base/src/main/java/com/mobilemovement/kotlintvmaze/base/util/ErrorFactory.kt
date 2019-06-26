package com.mobilemovement.kotlintvmaze.base.util

import java.lang.Exception

interface ErrorFactory {
    fun createEmptyErrorMessage(): String

    fun createApiErrorMessage(e: Exception): String
}
