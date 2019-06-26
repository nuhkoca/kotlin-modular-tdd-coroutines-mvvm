package com.mobilemovement.kotlintvmaze.base.util

import android.content.Context
import com.mobilemovement.kotlintvmaze.base.R
import javax.inject.Inject

class DefaultErrorFactory @Inject constructor(private val context: Context) : ErrorFactory {
    override fun createEmptyErrorMessage(): String = context.getString(R.string.empty_state_message)

    override fun createApiErrorMessage(e: Exception): String = e.message.toString()
}
