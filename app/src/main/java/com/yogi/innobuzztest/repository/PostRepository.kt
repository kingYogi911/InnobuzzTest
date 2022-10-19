package com.yogi.innobuzztest.repository

import com.yogi.innobuzztest.data.DataState
import com.yogi.innobuzztest.data.posts.PostItem
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Flow<DataState<List<PostItem>>>
    suspend fun forceReloadFromServer(): Flow<DataState<Boolean>>
}