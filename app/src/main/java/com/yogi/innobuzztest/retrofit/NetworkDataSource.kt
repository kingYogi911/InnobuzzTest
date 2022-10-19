package com.yogi.innobuzztest.retrofit

import com.yogi.innobuzztest.data.posts.PostItem
import retrofit2.Response
import retrofit2.http.GET

interface NetworkDataSource {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostItem>>
}