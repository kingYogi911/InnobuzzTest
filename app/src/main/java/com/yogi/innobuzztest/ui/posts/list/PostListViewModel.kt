package com.yogi.innobuzztest.ui.posts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.innobuzztest.data.DataState
import com.yogi.innobuzztest.data.posts.PostItem
import com.yogi.innobuzztest.repository.PostRepository
import com.yogi.innobuzztest.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _showProgress = MutableLiveData<String?>(null)
    val showProgress get() = _showProgress.asLiveData()

    private val _showError = MutableSharedFlow<Throwable>()
    val showError get() = _showError.asSharedFlow()

    private val _data = MutableLiveData<List<PostItem>>()
    val data get() = _data.asLiveData()

    init {
        reloadFromServer()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getPosts().collectLatest {
                when (it) {
                    is DataState.Error -> {
                        _showProgress.value = null
                        _showError.emit(it.t)
                    }
                    is DataState.Success -> {
                        _data.value = it.result as List<PostItem>
                        _showProgress.value = null
                    }
                    is DataState.Loading -> {
                        _showProgress.value = it.msg
                    }
                }
            }
        }
    }

    private fun reloadFromServer() {
        viewModelScope.launch {
            repository.forceReloadFromServer().collectLatest {
                when (it) {
                    is DataState.Error -> {
                        _showProgress.value = null
                        _showError.emit(it.t)
                    }
                    is DataState.Loading -> _showProgress.value = it.msg
                    is DataState.Success -> {
                        loadData()
                    }
                }
            }
        }
    }

    fun refresh() {
        reloadFromServer()
    }
}