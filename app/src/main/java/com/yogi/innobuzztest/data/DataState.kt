package com.yogi.innobuzztest.data

sealed class DataState<out R> {
    class Success<out T>(val result: T) : DataState<T>()
    class Error(val t: Throwable) : DataState<Nothing>()
    class Loading(val msg: String) : DataState<Nothing>()
}