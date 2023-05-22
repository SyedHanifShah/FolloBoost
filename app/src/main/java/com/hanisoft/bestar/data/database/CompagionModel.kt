package com.hanisoft.bestar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CompagionModel(
    @PrimaryKey val id: String,

    val avatar: String,
    val currentFollowers: String,
    val currentFollowing: String,
    val date: String,
    val packageFollowers: String,
    val uniqueId: String
)
