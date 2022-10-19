package com.yogi.innobuzztest.repository

import com.yogi.innobuzztest.data.DataState
import com.yogi.innobuzztest.data.posts.PostItem
import com.yogi.innobuzztest.retrofit.NetworkDataSource
import com.yogi.innobuzztest.room.DatabaseDao
import com.yogi.innobuzztest.utils.InternetConnectivityUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository_Impl @Inject constructor(
    private val internetConnectivityUtil: InternetConnectivityUtil,
    private val networkDataSource: NetworkDataSource,
    private val dao: DatabaseDao
) : PostRepository {
    override suspend fun getPosts(): Flow<DataState<List<PostItem>>> = flow {
        emit(DataState.Loading("Loading from Database"))
        withContext(Dispatchers.IO) {
            dao.getAllPosts() // fetch from database
        }.also { emit(DataState.Success(it)) }
    }.catch {
        emit(DataState.Error(Throwable("Unknown Exception")))
    }

    override suspend fun forceReloadFromServer(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading("Fetching from server"))
        if (internetConnectivityUtil.hasInternetConnection()) {
            withContext(Dispatchers.IO) {
                val list = networkDataSource.getPosts().body()!!
                dao.insertPosts(list)
            }.also { emit(DataState.Success(true)) }
        } else {
            emit(DataState.Error(Throwable("No Internet")))
        }
    }.catch {
        emit(DataState.Error(Throwable("Unknown Exception")))
    }
}