package com.yogi.innobuzztest.di

import android.content.Context
import androidx.room.Room
import com.yogi.innobuzztest.room.AppDatabase
import com.yogi.innobuzztest.room.DatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideDatabaseDao(database: AppDatabase): DatabaseDao {
        return database.databaseDao()
    }

}