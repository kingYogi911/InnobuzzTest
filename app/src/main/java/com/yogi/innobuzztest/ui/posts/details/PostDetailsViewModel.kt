package com.yogi.innobuzztest.ui.posts.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yogi.innobuzztest.data.posts.PostItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val data = MutableLiveData<PostItem>()

    init {
        data.value = savedStateHandle.get("item")
    }
}