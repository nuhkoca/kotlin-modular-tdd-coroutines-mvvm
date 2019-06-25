package com.mobilemovement.kotlintvmaze.base

import com.mobilemovement.kotlintvmaze.base.Status.EMPTY
import com.mobilemovement.kotlintvmaze.base.Status.ERROR
import com.mobilemovement.kotlintvmaze.base.Status.LOADING
import com.mobilemovement.kotlintvmaze.base.Status.SUCCESS

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> empty(msg: String): Resource<T> {
            return Resource(EMPTY, null, msg)
        }
    }
}
