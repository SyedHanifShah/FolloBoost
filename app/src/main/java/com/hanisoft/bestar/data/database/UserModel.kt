package com.hanisoft.bestar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserModel(
   @PrimaryKey val id: String,
    val __v: Int,
    val avtar: String,
    val coins: String,
    val date: String,
    val follower: String,
    val following: String,
    val name: String,
    val posts: String,
    val uniqueId: String
)
