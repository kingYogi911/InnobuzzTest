package com.yogi.innobuzztest.di

import com.yogi.innobuzztest.repository.PostRepository
import com.yogi.innobuzztest.repository.PostRepository_Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsPostRepository(postRepository_impl: PostRepository_Impl): PostRepository
}