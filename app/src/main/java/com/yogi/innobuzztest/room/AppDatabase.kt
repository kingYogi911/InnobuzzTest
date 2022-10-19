package com.yogi.innobuzztest.room;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yogi.innobuzztest.data.posts.PostItem

@Database(
    entities = [PostItem::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object {
        const val DATABASE_NAME = "local_database"
        const val DATABASE_VERSION = 1
    }
}
