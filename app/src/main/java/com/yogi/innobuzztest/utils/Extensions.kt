package com.yogi.innobuzztest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this