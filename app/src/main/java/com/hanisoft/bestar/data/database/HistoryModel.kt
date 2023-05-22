package com.hanisoft.bestar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class HistoryModel(
    @PrimaryKey val uniqueId:String,
    val packageName:String,
    val packageFollwer:String,
    val remainFollower:String,
    val gainFollwer:String,
)

