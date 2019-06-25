package com.mobilemovement.kotlintvmaze.base.util.mapper

interface Mapper<in T, out R> {
    fun map(input: T): R
}
