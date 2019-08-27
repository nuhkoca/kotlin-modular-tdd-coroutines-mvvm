package com.mobilemovement.kotlintvmaze.base.util.mapper

interface Mapper<in T, out R> {
    operator fun invoke(input: T): R
}
