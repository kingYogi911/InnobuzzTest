package com.yogi.innobuzztest.room;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yogi.innobuzztest.data.posts.PostItem

@Dao
public interface DatabaseDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertPosts(posts: List<PostItem>): List<Long>

    @Query("SELECT * FROM PostItem")
    suspend fun getAllPosts(): List<PostItem>

}
