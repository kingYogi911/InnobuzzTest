package com.yogi.innobuzztest.data.posts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PostItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String
) : Serializable